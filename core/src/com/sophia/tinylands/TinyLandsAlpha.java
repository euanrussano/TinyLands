package com.sophia.tinylands;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sophia.tinylands.buildings.*;
import com.sophia.tinylands.resources.Resource;
import com.sophia.tinylands.resources.ResourceInstance;
import com.sophia.tinylands.resources.ResourceRepository;

import java.util.HashMap;
import java.util.Map;

public class TinyLandsAlpha extends ApplicationAdapter implements EventListener, InputProcessor {

	private Stage mainStage;
	private Stage uiStage;
	private Group placingBuilding;
	private BuildingFactory buildingFactory;
	private Map<String, Label> resourceLabels;
	private PlayerManager playerManager;
	private AssetManager assetManager;
	private boolean win;
	private boolean lost;

	@Override
	public void create () {
		String TEXTURE_ATLAS = "spritesheets/medievalRTS.atlas";
		String pineTreeRegion = "medievalEnvironment_04.png";
		String stoneRegion = "medievalEnvironment_10.png";
		String grassRegion = "medievalTile_57.png";

		assetManager = new AssetManager();
		assetManager.load(TEXTURE_ATLAS, TextureAtlas.class);
		assetManager.load("you-win.png", Texture.class);
		assetManager.load("game-over.png", Texture.class);
		assetManager.finishLoading();
		TextureAtlas spritesheet = assetManager.get(TEXTURE_ATLAS);


		Json json = new Json();
		json.addClassTag("Building", Building.class);
		json.addClassTag("Resource", Resource.class);
		json.addClassTag("ResourceInstance", ResourceInstance.class);
		json.addClassTag("BuildingInstance", BuildingInstance.class);
		FileHandle allResources = Gdx.files.internal("gameData/allResources.json");
		FileHandle allBuildings = Gdx.files.internal("gameData/allBuildings.json");
		FileHandle playerData = Gdx.files.internal("playerData/playerData.json");
		ResourceRepository resourceRepository = json.fromJson(ResourceRepository.class, allResources.readString());
		BuildingRepository buildingRepository = json.fromJson(BuildingRepository.class, allBuildings.readString());
		buildingRepository.initialize();
		playerManager = json.fromJson(PlayerManager.class, playerData.readString());
		System.out.println(buildingRepository);

		mainStage = new Stage();
		uiStage = new Stage();
		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(uiStage);
		im.addProcessor(mainStage);
		im.addProcessor(this);
		Gdx.input.setInputProcessor(im);

		int worldWidth = 100;
		int worldHeight = 100;
		Group world = new Group();
		for (int x = 0; x< worldWidth; x++){
			for (int y = 0; y< worldHeight; y++){
				Actor grassTile = new Image(spritesheet.findRegion(grassRegion));
				grassTile.setPosition(x*64, y*64);
				world.addActor(grassTile);
			}
		}
		mainStage.addActor(world);

		Group naturalResources = new Group();
		naturalResources.setName("Natural Resources");
		for (int x = 0; x< worldWidth; x++){
			for (int y = 0; y< worldHeight; y++){
				float rand = MathUtils.random();
				if (rand < 0.4){
					Actor tree = new Image(spritesheet.findRegion(pineTreeRegion));
					tree.setPosition(x*64, y*64);
					naturalResources.addActor(tree);
				} else if (rand < 0.6){
					Actor stone = new Image(spritesheet.findRegion(stoneRegion));
					stone.setPosition(x*64, y*64);
					naturalResources.addActor(stone);
				}
			}
		}
		mainStage.addActor(naturalResources);


		buildingFactory = new BuildingFactory(buildingRepository, spritesheet);

		Group buildings = new Group();
		buildings.setName("Buildings");
		mainStage.addActor(buildings);

//		Group townCenter = buildingFactory.createBuilding("Town Center");
//		townCenter.setPosition(300, 200);
//		buildings.addActor(townCenter);


		Skin skin = new Skin();

		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888 );
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("default", new Texture(pixmap));
		skin.addRegions(spritesheet);

		// Store the default libGDX font under the name "default".
		skin.add("default", new BitmapFont());

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = skin.getFont("default");
		labelStyle.fontColor = Color.BLACK;
		skin.add("default", labelStyle);

		Table table = new Table();
		table.setFillParent(true);
		uiStage.addActor(table);

		// UI top row
		Table topBar = new Table();
		topBar.setBackground(skin.getDrawable("default"));
		table.add(topBar).left();

		Label populationLabel = new Label("Population:", skin);
		Label populationAmount = new Label("   0", skin);

		Label foodLabel = new Label("Food:", skin);
		Label foodAmount = new Label("   0", skin);

		Label woodLabel = new Label("Wood:", skin);
		Label woodAmount = new Label("   0", skin);

		Label stoneLabel = new Label("Stone:", skin);
		Label stoneAmount = new Label("   0", skin);

		resourceLabels = new HashMap<>();
		resourceLabels.put("Population", populationAmount);
		resourceLabels.put("Food", foodAmount);
		resourceLabels.put("Wood", woodAmount);
		resourceLabels.put("Stone", stoneAmount);

		topBar.add(populationLabel).padRight(10);
		topBar.add(populationAmount).padRight(10);
		topBar.add(foodLabel).padRight(10);
		topBar.add(foodAmount).padRight(10);
		topBar.add(woodLabel).padRight(10);
		topBar.add(woodAmount).padRight(10);
		topBar.add(stoneLabel).padRight(10);
		topBar.add(stoneAmount).padRight(10);

		// UI middle row table
		table.row();
		table.add().expandY().expandX();
		table.row();
		// UI bottom row

		TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.up =  skin.newDrawable("default", Color.DARK_GRAY);
		buttonStyle.down =  skin.newDrawable("default", Color.LIGHT_GRAY);
		buttonStyle.font = new BitmapFont();

		Table bottomBar = new Table();
		bottomBar.setBackground(skin.newDrawable("default", Color.LIGHT_GRAY));

		Table buildingButtons = new Table();
		bottomBar.add(buildingButtons);

		for (Building building : buildingRepository.findAll()){
			BuildingButton houseButton = new BuildingButton(skin.getDrawable(building.getTexture()));
			houseButton.setBuildingName(building.getName());
			buildingButtons.add(houseButton).pad(10).left();
			houseButton.addListener(this);
		}

		TextButton endTurnButton = new TextButton("End Turn", buttonStyle);
		endTurnButton.addListener(this);
		bottomBar.add(endTurnButton).expandX().right().pad(10);


		table.add(bottomBar).height(64).width(Gdx.graphics.getWidth()).bottom();
		table.setDebug(true);

		// initialize the resource labels according the player amount
		updateResourceLabels();

		win = false;
		lost = false;
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();

		mainStage.act(delta);
		uiStage.act(delta);

		ScreenUtils.clear(Color.BLACK);

		mainStage.draw();

		if (placingBuilding != null){
			Image buildingImage = placingBuilding.findActor(Image.class.getSimpleName());
			buildingImage.setColor(Color.WHITE);
			for (Actor actorGroup : mainStage.getActors()){
				if (actorGroup.getName() == null) continue;
				if (!actorGroup.getName().equals("Natural Resources") & !actorGroup.getName().equals("Buildings"))
					continue;
				for (Actor actor : ((Group)actorGroup).getChildren()){
					Rectangle rec1 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
					Rectangle rec2 = new Rectangle(placingBuilding.getX(), placingBuilding.getY(), placingBuilding.getWidth(), placingBuilding.getHeight());
					if (rec1.overlaps(rec2)){
						buildingImage.setColor(Color.RED);
						break;
					}
				}
			}
			Batch batch = mainStage.getBatch();
			batch.begin();
			placingBuilding.draw(batch, 1);
			batch.end();
		}


		uiStage.draw();

		if (win){
			Texture youWinMessage = assetManager.get("you-win.png", Texture.class);
			Batch batch = mainStage.getBatch();
			batch.begin();
			batch.draw(youWinMessage, 0, Gdx.graphics.getHeight()/2);
			batch.end();
		} else if (lost){
			Texture gameOverMessage = assetManager.get("game-over.png", Texture.class);
			Batch batch = mainStage.getBatch();
			batch.begin();
			batch.draw(gameOverMessage, 0, Gdx.graphics.getHeight()/2);
			batch.end();
		}

	}
	
	@Override
	public void dispose () {

	}

	@Override
	public boolean handle(Event event) {
		if (event instanceof ChangeListener.ChangeEvent){
			ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent) event;
			Actor actor = changeEvent.getTarget();
			if (actor instanceof TextButton textButton){
				if (textButton.getLabel().getText().toString().equals("End Turn")){
					System.out.println("here end turn");
					endTurn();
					return true;
				}
			}
			return true;
		}
		if (event instanceof InputEvent inputEvent){
			if (inputEvent.getType() == InputEvent.Type.touchDown){
				Actor actor = inputEvent.getTarget();

				Group hitGroup = actor.getParent();
				if (hitGroup instanceof BuildingButton){
					placingBuilding = buildingFactory.createBuilding(((BuildingButton)hitGroup).getBuildingName());

					return true;
				}
			}
		}
		return false;
	}

	private void endTurn() {
		playerManager.update();
		updateResourceLabels();
		checkWinOrLoseCondition();
	}

	private void checkWinOrLoseCondition() {
		ResourceInstance population = playerManager.getResourceInstance("Population");
		if (population != null){
			if (population.getQuantity() >= 20){
				win = true;
			}
		}else {
			lost = true;
			for (String resourceName : resourceLabels.keySet()){
				ResourceInstance resourceInstance = playerManager.getResourceInstance(resourceName);
				if (resourceInstance != null){
					if (resourceInstance.getQuantity()>0 & !resourceName.equals("Population")){
						lost = false;
						break;
					}
				}

			}
		}

	}

	private void updateResourceLabels() {
		for (Map.Entry<String, Label> entry : resourceLabels.entrySet()){
			String resourceName = entry.getKey();
			Label label = entry.getValue();
			ResourceInstance instance = playerManager.getResourceInstance(resourceName);
			label.setText(instance != null ? instance.getQuantity() : 0);

		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.ESCAPE){
			placingBuilding = null;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 coords =  new Vector2(screenX, screenY);
		mainStage.screenToStageCoordinates(coords);
		if (placingBuilding != null){
			Image buildingImage = placingBuilding.findActor(Image.class.getSimpleName());
			if (!buildingImage.getColor().equals(Color.RED)){

				BuildingInstance buildingInstance = new BuildingInstance();
				Building building = BuildingRepository.getInstance().findByName(placingBuilding.getName());
				if (playerManager.hasEnoughResources(building.getMaterialsToBuild())){
					System.out.println("has enough");
					for (ResourceInstance resourceInstance : building.getMaterialsToBuild()){
						playerManager.substractFromResources(resourceInstance);
					}

					buildingInstance.setBuilding(building);
					buildingInstance.setPosition(placingBuilding.getX(), placingBuilding.getY());
					buildingInstance.addAttributes(building.getAttributes());
					playerManager.addBuildingInstance(buildingInstance);
					updateResourceLabels();

					Group buildings = mainStage.getRoot().findActor("Buildings");
					buildings.addActor(placingBuilding);
					placingBuilding = null;
				}

			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if (placingBuilding != null){
			Vector2 coords =  new Vector2(screenX, screenY);
			mainStage.screenToStageCoordinates(coords);
			placingBuilding.setPosition(coords.x - placingBuilding.getWidth()/2, coords.y - placingBuilding.getHeight()/2);
		}
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
