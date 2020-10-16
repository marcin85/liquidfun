package pl.marcindebski.escape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class Input extends GestureAdapter
{

    public abstract void tapHandler();

    public abstract void panHandler();

    private GestureDetector gestureDetector;
    private Vector3 touchPos;
    private OrthographicCamera camera;
    private float widthWithScale;
    private float heightWithScale;
    private Vector2 crop;

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
	touchPos.set(Gdx.input.getX(),
		Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
	camera.unproject(touchPos, crop.x, crop.y, widthWithScale,
		heightWithScale);

	panHandler();

	return false;

    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
	touchPos.set(Gdx.input.getX(),
		Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
	camera.unproject(touchPos, crop.x, crop.y, widthWithScale,
		heightWithScale);

	tapHandler();

	return false;
    }

    public void setInput(OrthographicCamera camera, float widthWithScale,
	    float heightWithScale, Vector2 crop)
    {

	this.camera = camera;
	this.widthWithScale = widthWithScale;
	this.heightWithScale = heightWithScale;
	this.crop = crop;

	touchPos = new Vector3();

	gestureDetector = new GestureDetector(20, 0.1f, 2, 0.15f, this);
	Gdx.input.setInputProcessor(gestureDetector);
    }

    public Vector3 getTouchPos()
    {
	return touchPos;
    }

}
