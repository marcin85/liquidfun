package pl.marcindebski.escape;

import pl.marcindebski.escape.levels.LevelRunner;
import pl.marcindebski.escape.levels.test.Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class Escape implements ApplicationListener {

	static public final int VIRTUAL_WIDTH = 640;
	static public final int VIRTUAL_HEIGHT = 420;
	static private final float ASPECT_RATIO = (float) VIRTUAL_WIDTH
			/ (float) VIRTUAL_HEIGHT;
	private Rectangle viewport;
	private float scale;
	static private float widthWithScale;

	static {
		new SharedLibraryLoader().load("liquidfun");
		new SharedLibraryLoader().load("liquidfun_jni");
	}

	public static float getWidthWithScale() {
		return widthWithScale;
	}

	static private float heightWithScale;

	public static float getHeightWithScale() {
		return heightWithScale;
	}

	static private Vector2 crop;

	static public Vector2 getCrop() {
		return crop;
	}

	static private float zoomFactor = 1;

	public static float getZoomFactor() {
		return zoomFactor;
	}

	public static void setZoomFactor(float zoomFactor) {
		Escape.zoomFactor = zoomFactor;
	}

	static private OrthographicCamera gameCamera;
	static private SpriteBatch batch;

	public static SpriteBatch getBatch() {
		return batch;
	}

	static public OrthographicCamera getGameCamera() {
		return gameCamera;
	}

	private LevelRunner levelRunner;

	@Override
	public void create() {

		gameCamera = new OrthographicCamera();
		gameCamera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		myResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch = new SpriteBatch();

		levelRunner = new LevelRunner();
		levelRunner.setLevel(new Test());

	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		gameCamera.update();
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
				(int) viewport.width, (int) viewport.height);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		levelRunner.run();

	}

	@Override
	public void resize(int width, int height) {

		myResize(width, height);

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	void myResize(int width, int height) {
		float aspectRatio = (float) width / (float) height;

		crop = new Vector2(0f, 0f);

		if (aspectRatio > ASPECT_RATIO) {
			scale = (float) height / (float) VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
		}

		else if (aspectRatio < ASPECT_RATIO) {
			scale = (float) width / (float) VIRTUAL_WIDTH;
			crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
		} else {
			scale = (float) width / (float) VIRTUAL_WIDTH;
		}

		widthWithScale = (float) VIRTUAL_WIDTH * scale;
		heightWithScale = (float) VIRTUAL_HEIGHT * scale;

		viewport = new Rectangle(crop.x, crop.y, widthWithScale,
				heightWithScale);
	};

}
