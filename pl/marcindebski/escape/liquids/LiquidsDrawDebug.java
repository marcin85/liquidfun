package pl.marcindebski.escape.liquids;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.List;

import pl.marcindebski.escape.Escape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.google.fpl.liquidfun.Body;
import com.google.fpl.liquidfun.BodyType;
import com.google.fpl.liquidfun.CircleShape;
import com.google.fpl.liquidfun.Fixture;
import com.google.fpl.liquidfun.ParticleSystem;
import com.google.fpl.liquidfun.PolygonShape;
import com.google.fpl.liquidfun.Shape.Type;
import com.google.fpl.liquidfun.Transform;
import com.google.fpl.liquidfun.Vec2;

public class LiquidsDrawDebug extends LiquidsDraw
{

    private final Color SHAPE_STATIC = new Color(0.5f, 0.9f, 0.5f, 1);
    private final Color SHAPE_KINEMATIC = new Color(0.5f, 0.5f, 0.9f, 1);
    private final Color SHAPE_NOT_ACTIVE = new Color(0.5f, 0.5f, 0.3f, 1);
    private final Color SHAPE_AWAKE = new Color(0.9f, 0.7f, 0.7f, 1);
    private final Color SHAPE_NOT_AWAKE = new Color(0.6f, 0.6f, 0.6f, 1);

    protected ShapeRenderer renderer;
    private final static Vector2[] vertices = new Vector2[1000];

    com.badlogic.gdx.physics.box2d.Transform libgdxTranform = new com.badlogic.gdx.physics.box2d.Transform();

    public LiquidsDrawDebug()
    {
	
	//createTexture();

	renderer = new ShapeRenderer();

	// initialize vertices array
	for (int i = 0; i < vertices.length; i++)
	    vertices[i] = new Vector2();
    }

    private final Vector2 f = new Vector2();
    private final Vector2 v = new Vector2();
    private final Vector2 lv = new Vector2();

    private void gdxDrawSolidPolygon(Vector2[] vertices, int vertexCount,
	    Color color)
    {

	renderer.setColor(color.r, color.g, color.b, color.a);
	lv.set(vertices[0]);
	f.set(vertices[0]);
	for (int i = 1; i < vertexCount; i++)
	{
	    Vector2 v = vertices[i];
	    renderer.line(lv.x, lv.y, v.x, v.y);
	    lv.set(v);
	}
	renderer.line(f.x, f.y, lv.x, lv.y);

    }
    
    float smoothstep(float x) { return x * x * (3 - 2 * x); }
    private void createTexture(){

	
//	ByteBuffer bb = ByteBuffer.allocateDirect(8);
//        bb.order(ByteOrder.nativeOrder());
//        IntBuffer ib = bb.asIntBuffer();
//        ib.put(0);
//        
//	Gdx.gl20.glGenTextures(1, ib);
//	int TSIZE = 64;
//	char[][][] tex = new char[TSIZE][TSIZE][4];
//	
//	for (int y = 0; y < TSIZE; y++)
//	{
//		for (int x = 0; x < TSIZE; x++)
//		{
//			float fx = (x + 0.5f) / TSIZE * 2 - 1;
//			float fy = (y + 0.5f) / TSIZE * 2 - 1;
//			float dist = (float) Math.sqrt(fx * fx + fy * fy);
//			char intensity = (char)(dist <= 1 ? smoothstep(1 - dist) * 255 : 0);
//			tex[y][x][0] = tex[y][x][1] = tex[y][x][2] = 128;
//			tex[y][x][3] = intensity;
//		}
//	}
//	
//	Gdx.gl20.glEnable(Gdx.gl20.GL_TEXTURE_2D);
//	Gdx.gl20.glBindTexture(Gdx.gl20.GL_TEXTURE_2D, 0);
//	
//	Gdx.gl20.glTexParameteri(Gdx.gl20.GL_TEXTURE_2D, Gdx.gl20.GL_TEXTURE_WRAP_S, Gdx.gl20.GL_CLAMP_TO_EDGE);
//	Gdx.gl20.glTexParameteri(Gdx.gl20.GL_TEXTURE_2D, Gdx.gl20.GL_TEXTURE_WRAP_T, Gdx.gl20.GL_CLAMP_TO_EDGE);
//	Gdx.gl20.glTexParameterf(Gdx.gl20.GL_TEXTURE_2D, Gdx.gl20.GL_GENERATE_MIPMAP, Gdx.gl20.GL_TRUE);
//
//	Gdx.gl20.glTexParameteri(Gdx.gl20.GL_TEXTURE_2D, Gdx.gl20.GL_TEXTURE_MAG_FILTER, Gdx.gl20.GL_LINEAR);
//	Gdx.gl20.glTexParameteri(Gdx.gl20.GL_TEXTURE_2D, Gdx.gl20.GL_TEXTURE_MIN_FILTER, Gdx.gl20.GL_LINEAR_MIPMAP_LINEAR);
//	
//	
//        ByteBuffer bb2 = ByteBuffer.allocateDirect(90000);
//        bb.order(ByteOrder.nativeOrder());
//        CharBuffer cb = bb2.asCharBuffer();
//        
//        
//	for (int y = 0; y < TSIZE; y++)
//	{
//		for (int x = 0; x < TSIZE; x++)
//		{
//		    cb.put(tex[y][x][0]);
//		}
//	}
//	
//	Gdx.gl20.glTexImage2D(Gdx.gl20.GL_TEXTURE_2D, 0, Gdx.gl20.GL_RGBA, TSIZE, TSIZE, 0, Gdx.gl20.GL_RGBA, Gdx.gl20.GL_UNSIGNED_BYTE, cb);
//	
//	Gdx.gl20.glDisable(Gdx.gl20.GL_TEXTURE_2D);

	//Gdx.gl20.glEnable(Gdx.gl20.GL_POINT_SMOOTH);
	
	
	
    }
    
    
    
    private void drawParticle(Vector2 center, float radius, Vector2 axis)
    {
	
	Gdx.gl20.glEnable(Gdx.gl20.GL_TEXTURE_2D);
	Gdx.gl20.glBindTexture(Gdx.gl20.GL_TEXTURE_2D, 0);
	
	//Gdx.gl20.glEnable(Gdx.gl20.GL_POINT_SPRITE_OES);
	//Gdx.gl20.glTexEnvf(GL_POINT_SPRITE_OES, GL_COORD_REPLACE_OES, GL_TRUE);
	 float particle_size_multiplier = 3;  // because of falloff
	 float global_alpha = 1;  // none, baked in texture
	 

	     // Gdx.gl20.glPointSize(radius * 1 * particle_size_multiplier);

		Gdx.gl20.glEnable(Gdx.gl20.GL_BLEND);
		Gdx.gl20.glBlendFunc(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE);

		//Gdx.gl20.glEnableClientState(Gdx.gl20.GL_VERTEX_ARRAY);
		//Gdx.gl20.glVertexPointer(2, Gdx.gl20.GL_FLOAT, 0, center.x);
		
		//glColor4f(1, 1, 1, global_alpha);
	

    Gdx.gl20.glDrawArrays(Gdx.gl20.GL_POINTS, 0, 0);

   // Gdx.gl20.glDisableClientState(Gdx.gl20.GL_VERTEX_ARRAY);

    Gdx.gl20.glDisable(Gdx.gl20.GL_BLEND);
    Gdx.gl20.glDisable(Gdx.gl20.GL_TEXTURE_2D);
    //Gdx.gl20.glDisable(Gdx.gl.GL_POINT_SPRITE_OES);
	
    
    }
    
    //void drawParticleSprite(Vector2 center, float radius){
	
   // }
    

    private void drawSolidCircle(Vector2 center, float radius, Vector2 axis,
	    Color color)
    {
	//drawParticleSprite(center,radius);
	
	float angle = 0;
	float angleInc = 2 * (float) Math.PI / 20;
	renderer.setColor(color.r, color.g, color.b, color.a);
	for (int i = 0; i < 20; i++, angle += angleInc)
	{
	    v.set((float) Math.cos(angle) * radius + center.x,
		    (float) Math.sin(angle) * radius + center.y);
	    if (i == 0)
	    {
		lv.set(v);
		f.set(v);
		continue;
	    }
	    renderer.line(lv.x, lv.y, v.x, v.y);
	    lv.set(v);
	}
	renderer.line(f.x, f.y, lv.x, lv.y);
	renderer.line(center.x, center.y, 0, center.x + axis.x * radius,
		center.y + axis.y * radius, 0);
    }

    @Override
    public void render(Matrix4 cameraType, List<Body> bodies,
	    List<ParticleSystem> particleSystems)
    {

	renderer.setProjectionMatrix(cameraType);
	renderer.begin(ShapeType.Line);

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

	renderer.end();
    }

    protected void renderBody(Body body)
    {
	Transform transform = body.getTransform();
	Fixture fixture = body.getFixtureList();
	drawShape(fixture, transform, getColorByBody(body));

    }

    private Color getColorByBody(Body body)
    {
	if (body.isActive() == false)
	    return SHAPE_NOT_ACTIVE;
	else if (body.getType() == BodyType.staticBody)
	    return SHAPE_STATIC;
	else if (body.getType() == BodyType.kinematicBody)
	    return SHAPE_KINEMATIC;
	else if (body.isAwake() == false)
	    return SHAPE_NOT_AWAKE;
	else
	    return SHAPE_AWAKE;
    }

    private static Vector2 t = new Vector2();
    private static Vector2 axis = new Vector2();

    private void drawShape(Fixture fixture, Transform transform, Color color)
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
	    gdxDrawSolidPolygon(vertices, vertexCount, color);
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

		drawSolidCircle(new Vector2(x, y),
			Liquids.convertToBox(radius), new Vector2(0, 0),
			SHAPE_KINEMATIC);
	    }
	}

    }

}
