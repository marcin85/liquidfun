package pl.marcindebski.escape.physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Physics
{

    private com.badlogic.gdx.physics.box2d.World world;
    private Body body;
    private Box2DDebugRenderer debugRenderer;

    static final float WORLD_TO_BOX = 0.01f;
    static final float BOX_WORLD_TO = 100f;

    static final float BOX_STEP = 1 / 120f;
    static final int BOX_VELOCITY_ITERATIONS = 8;
    static final int BOX_POSITION_ITERATIONS = 3;
    float accumulator;

    public Physics()
    {

	world = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -20),
		true);

	debugRenderer = new Box2DDebugRenderer(true, true, true, true, false,
		false);

    }

    private float convertToBox(float x)
    {
	return x * WORLD_TO_BOX;
    }

    private void createBody(com.badlogic.gdx.physics.box2d.World world,
	    BodyDef.BodyType bodyType, Vector2 pos, float angle)
    {
	BodyDef bodyDef = new BodyDef();
	bodyDef.type = bodyType;
	bodyDef.position.set(convertToBox(pos.x), convertToBox(pos.y));
	bodyDef.angle = angle;
	body = world.createBody(bodyDef);
    }

    public void makeRectangle(float width, float height,
	    BodyDef.BodyType bodyType, float density, float restitution,
	    Vector2 pos, float angle)
    {
	createBody(world, bodyType, pos, angle);

	PolygonShape bodyShape = new PolygonShape();

	float w = convertToBox(width / 2f);
	float h = convertToBox(height / 2f);
	bodyShape.setAsBox(w, h);

	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.density = density;
	fixtureDef.restitution = restitution;
	fixtureDef.shape = bodyShape;

	body.createFixture(fixtureDef);
	bodyShape.dispose();
    }

    public void update(float dt)
    {
	accumulator += dt;
	while (accumulator > BOX_STEP)
	{
	    world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS,
		    BOX_POSITION_ITERATIONS);
	    accumulator -= BOX_STEP;
	}
    }

    public void drawDebugWorld(Matrix4 cameraType)
    {

	debugRenderer.render(world, cameraType);

    }

}
