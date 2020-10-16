package pl.marcindebski.escape.liquids;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.google.fpl.liquidfun.Body;
import com.google.fpl.liquidfun.CircleShape;
import com.google.fpl.liquidfun.Fixture;
import com.google.fpl.liquidfun.ParticleSystem;
import com.google.fpl.liquidfun.PolygonShape;
import com.google.fpl.liquidfun.Transform;
import com.google.fpl.liquidfun.Vec2;
import com.google.fpl.liquidfun.Shape.Type;

public class LiquidsDrawReal extends LiquidsDraw
{

    private final static Vector2[] vertices = new Vector2[1000];
    private com.badlogic.gdx.physics.box2d.Transform libgdxTranform = new com.badlogic.gdx.physics.box2d.Transform();

    public LiquidsDrawReal()
    {
	// initialize vertices array
	for (int i = 0; i < vertices.length; i++)
	    vertices[i] = new Vector2();
    }

    private final Vector2 f = new Vector2();
    private final Vector2 v = new Vector2();
    private final Vector2 lv = new Vector2();

    public void render(Matrix4 cameraType, List<Body> bodies,
	    List<ParticleSystem> particleSystems)
    {

	for (Iterator<Body> iter = bodies.iterator(); iter.hasNext();)
	{
	    Body body = iter.next();
	    if (body.isActive())
		renderBody(body);
	}

	for (ParticleSystem p : particleSystems)
	{
	    drawParticleSystem(p);
	}

    }

    private void gdxDrawSolidPolygon(Vector2[] vertices, int vertexCount)
    {

    }

    private void drawSolidCircle(Vector2 center, float radius, Vector2 axis)
    {

    }

    private void drawParticle(Vector2 center, float radius, Vector2 axis)
    {
	System.out.print("dfsd");

    }

    protected void renderBody(Body body)
    {
	Transform transform = body.getTransform();
	Fixture fixture = body.getFixtureList();
	drawShape(fixture, transform);

    }

    private static Vector2 t = new Vector2();
    private static Vector2 axis = new Vector2();

    private void drawShape(Fixture fixture, Transform transform)
    {

	if (fixture.getType() == Type.CIRCLE)
	{
	    CircleShape circle = (CircleShape) fixture.getShape();
	    t.set(new Vector2(circle.getPosX(), circle.getPosX()));
	    libgdxTranform.mul(t);
	    // drawSolidCircle(t, circle.getRadius(),
	    // axis.set(libgdxTranform.vals[com.badlogic.gdx.physics.box2d.Transform.COS],
	    // libgdxTranform.vals[com.badlogic.gdx.physics.box2d.Transform.SIN]),
	    // color);
	    return;
	}

	if (fixture.getType() == Type.POLYGON)
	{
	    PolygonShape chain = PolygonShape.dynamicCast(fixture.getShape());
	    int vertexCount = chain.getVertexCount();
	    for (int i = 0; i < vertexCount; i++)
	    {
		Vec2 v = chain.getVertex(i);
		vertices[i].x = v.getX();
		vertices[i].y = v.getY();

		mul(vertices[i], transform.getPosX(), transform.getPosY(),
			transform.getRotSin(), transform.getRotCos());
	    }
	    gdxDrawSolidPolygon(vertices, vertexCount);
	    return;
	}

    }

    void drawParticleSystem(ParticleSystem system)
    {
	float radius = system.getRadius();
	int particleCount = system.getParticleCount();
	if (particleCount != 0)
	{
	    for (int a = 0; a < particleCount; a++)
	    {
		float x = Liquids.convertToBox(system.getParticlePositionX(a));
		float y = Liquids.convertToBox(system.getParticlePositionY(a));

		drawParticle(new Vector2(x, y), Liquids.convertToBox(radius),
			new Vector2(0, 0));
	    }
	}

    }

}
