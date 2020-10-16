package pl.marcindebski.escape.levels.test;

import pl.marcindebski.escape.Escape;
import pl.marcindebski.escape.Input;
import pl.marcindebski.escape.levels.Level;
import pl.marcindebski.escape.levels.hud.Hud;
import pl.marcindebski.escape.liquids.Liquids;
import pl.marcindebski.escape.liquids.LiquidsDrawDebug;
import pl.marcindebski.escape.physics.Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Test extends Input implements Level
{

    Physics physicsWorld;
    Liquids liquidsWorld;

    private Hud hud;

    public Test()
    {

	physicsWorld = new Physics();
	liquidsWorld = new Liquids();
	liquidsWorld.setRenderer(new LiquidsDrawDebug());
	// liquidsWorld.setRenderer(new LiquidsDrawReal());

	hud = new Hud();
	hud.createCamera();

	setInput(Escape.getGameCamera(), Escape.getWidthWithScale(),
		Escape.getHeightWithScale(), Escape.getCrop());

	physicsWorld.makeRectangle(2, 2, BodyType.DynamicBody, .4f, .1f,
		new Vector2(10, 30), 0);

	physicsWorld.makeRectangle(60, 2, BodyType.StaticBody, 1, .1f,
		new Vector2(0, -20), .2f);

	liquidsWorld.createBody(0, 0, Escape.VIRTUAL_WIDTH, 5, false);
	liquidsWorld.createBody(0, 0, 3, Escape.VIRTUAL_HEIGHT, false);
	liquidsWorld.createBody(Escape.VIRTUAL_WIDTH-5, 0, 3, Escape.VIRTUAL_HEIGHT, false);
	liquidsWorld.createBody(100, 30, 300, 10, false);
	liquidsWorld.createPointerBody();
	liquidsWorld.createParticles(3.5f, 360, 200, 40, 200);

    }

    @Override
    public void tapHandler()
    {

	liquidsWorld.createBody((int) getTouchPos().x, Gdx.graphics.getHeight()
		- (int) getTouchPos().y, 30, 30, true);

    }

    @Override
    public void panHandler()
    {

	liquidsWorld.pointerBody.setTransform(
		Liquids.convertToBox(getTouchPos().x),
		Gdx.graphics.getHeight()
			- Liquids.convertToBox(getTouchPos().y), 0);
	// Escape.setZoomFactor(Escape.getZoomFactor() + .01f);
	// Escape.getGameCamera().zoom = Escape.getZoomFactor();

    }

    @Override
    public void runGameLogic()
    {
	// physicsWorld.update(1 / 300f);
	liquidsWorld.update(1 / 300f);

    }

    @Override
    public void renderPrimitives()
    {
	// physicsWorld.drawDebugWorld(camera.combined);
	liquidsWorld.drawLiquids(Escape.getGameCamera().combined);

    }

    @Override
    public void renderBath()
    {

	hud.getCamera().update();
	Escape.getBatch().setProjectionMatrix(hud.getCamera().combined);
	hud.render(Escape.getBatch());

    }

}
