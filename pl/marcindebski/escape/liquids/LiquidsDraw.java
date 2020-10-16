package pl.marcindebski.escape.liquids;

import java.util.List;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.google.fpl.liquidfun.Body;

import com.google.fpl.liquidfun.ParticleSystem;

public abstract class LiquidsDraw
{

    abstract public void render(Matrix4 cameraType, List<Body> bodies,
	    List<ParticleSystem> particleSystems);

    protected Vector2 mul(Vector2 v, float x, float y, float sin, float cos)
    {
	float rx = x + cos * v.x + -sin * v.y;
	float ry = y + sin * v.x + cos * v.y;
	v.x = rx;
	v.y = ry;
	return v;
    }

}
