package pl.marcindebski.escape.liquids;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Matrix4;
import com.google.fpl.liquidfun.Body;
import com.google.fpl.liquidfun.BodyDef;
import com.google.fpl.liquidfun.BodyType;
import com.google.fpl.liquidfun.FixtureDef;
import com.google.fpl.liquidfun.ParticleFlag;
import com.google.fpl.liquidfun.ParticleGroupDef;
import com.google.fpl.liquidfun.ParticleGroupFlag;
import com.google.fpl.liquidfun.ParticleSystem;
import com.google.fpl.liquidfun.ParticleSystemDef;
import com.google.fpl.liquidfun.PolygonShape;
import com.google.fpl.liquidfun.World;

public class Liquids
{

    private World liquidsWorld;
    private LiquidsDraw renderer;

    private List<Body> bodies = new ArrayList<Body>();
    private List<ParticleSystem> particleSystems = new ArrayList<ParticleSystem>();

    public Body pointerBody;

    static final float WORLD_TO_BOX = 1.0f;
    static final float BOX_WORLD_TO = 100f;

    static final float BOX_STEP = 1 / 1500f;
    static final int BOX_VELOCITY_ITERATIONS = 5;
    static final int BOX_POSITION_ITERATIONS = 4;
    static final int BOX_PARTICLES_ITERATIONS = 3;
    float accumulator;

    public Liquids()
    {

	liquidsWorld = new World(0, -550000f);

	// liquidsWorld.setDebugDraw(debugDraw);
	// liquidsWorld.drawDebugData();

    }

    public void setRenderer(LiquidsDraw renderer)
    {
	this.renderer = renderer;
    };

    public static float convertToBox(float x)
    {
	return x * WORLD_TO_BOX;
    }

    public void createBody(int x, int y, int sizeX, int sizeY, boolean dynamic)
    {
	BodyDef groundBodyDef = new BodyDef();
	groundBodyDef.setPosition(convertToBox(x + sizeX / 2),
		convertToBox((y + sizeY / 2)));
	if (dynamic)
	    groundBodyDef.setType(BodyType.dynamicBody);
	else
	    groundBodyDef.setType(BodyType.staticBody);
	groundBodyDef.setActive(true);
	Body groundBody = liquidsWorld.createBody(groundBodyDef);

	PolygonShape groundBox = new PolygonShape();
	groundBox.setAsBox(convertToBox(sizeX / 2), convertToBox(sizeY / 2));

	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.setDensity(0.2f);
	fixtureDef.setShape(groundBox);

	groundBody.createFixture(fixtureDef);

	bodies.add(groundBody);

    }

    public void createPointerBody()
    {

	BodyDef bodyDef = new BodyDef();
	bodyDef.setType(BodyType.staticBody);
	bodyDef.setPosition(convertToBox(100.0f), convertToBox(160.0f));
	pointerBody = liquidsWorld.createBody(bodyDef);

	PolygonShape dynamicBox = new PolygonShape();
	dynamicBox.setAsBox(convertToBox(10.0f), convertToBox(10.0f));

	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.setShape(dynamicBox);

	pointerBody.createFixture(fixtureDef);
	bodies.add(pointerBody);

    }

    public void createParticles(float dropSize, int x, int y, int sizeX,
	    int sizeY)
    {

	ParticleSystemDef particleSystemDef = new ParticleSystemDef();
	// particleSystemDef.setDestroyByAge(true);
	particleSystemDef.setRadius(convertToBox(dropSize));
	ParticleSystem particleSystem = liquidsWorld
		.createParticleSystem(particleSystemDef);
	particleSystem.setStrictContactCheck(true);
	liquidsWorld.setWarmStarting(true);
	liquidsWorld.setContinuousPhysics(true);
	liquidsWorld.setSubStepping(true);
	particleSystems.add(particleSystem);

	ParticleGroupDef pd = new ParticleGroupDef();
	PolygonShape shape = new PolygonShape();
	shape.setAsBox(convertToBox(sizeX), convertToBox(sizeY));
	pd.setShape(shape);
	pd.setFlags(ParticleFlag.waterParticle);
	pd.setFlags(ParticleGroupFlag.solidParticleGroup);
	// pd.setStrength(10f);
	// pd.setAngle(-0.5f);
	pd.setAngularVelocity(.2f);

	pd.setPosition(convertToBox(x), convertToBox(y));
	particleSystem.createParticleGroup(pd);

    }

    public void update(float dt)
    {
	accumulator += dt;
	while (accumulator > BOX_STEP)
	{
	    liquidsWorld.step(BOX_STEP, BOX_VELOCITY_ITERATIONS,
		    BOX_POSITION_ITERATIONS, BOX_PARTICLES_ITERATIONS);
	    accumulator -= BOX_STEP;
	}

    }

    public void drawLiquids(Matrix4 cameraType)
    {
	renderer.render(cameraType, bodies, particleSystems);

    }

}
