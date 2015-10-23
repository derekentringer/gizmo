package com.derekentringer.gizmo.component.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.derekentringer.gizmo.component.stage.GameStage
import com.derekentringer.gizmo.component.stage.HudStage
import com.derekentringer.gizmo.manager.LocalDataManager
import com.derekentringer.gizmo.settings.Constants
import com.derekentringer.gizmo.util.GameLevelUtils
import com.derekentringer.gizmo.util.log.GLog

class GameScreen : Screen {

    private val mHudStage: HudStage
    private val mGameStage: GameStage

    private var mViewPort: Rectangle? = null

    private var mLevelToLoad = 0

    //private FPSLogger mFpsLogger = new FPSLogger();

    init {
        GameLevelUtils.buildGameLevelList()
        mGameStage = GameStage()
        mHudStage = HudStage(mGameStage)
        mHudStage.addListener(mGameStage)
        if (LocalDataManager.loadPlayerActorData() != null) {
            mLevelToLoad = LocalDataManager.loadPlayerActorData()!!.currentLevel
        }
        mGameStage.init(GameLevelUtils.gameLevels[mLevelToLoad])
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        //clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //set the viewport
        Gdx.gl.glViewport(mViewPort!!.x.toInt(),
                mViewPort!!.y.toInt(),
                mViewPort!!.width.toInt(),
                mViewPort!!.height.toInt())

        //update the game stage
        mGameStage.act(delta)
        mGameStage.draw()

        //update the hud stage
        mHudStage.act(delta)
        mHudStage.draw()

        //mFpsLogger.log();
    }

    override fun resize(width: Int, height: Int) {
        val aspectRatio = width.toFloat() / height.toFloat()
        var scale = 1f
        val crop: Vector2
        val w: Float
        val h: Float

        crop = Vector2(0f, 0f)

        if (aspectRatio > Constants.ASPECT_RATIO) {
            scale = height.toFloat() / Constants.GAME_HEIGHT.toFloat()
            crop.x = (width - Constants.GAME_WIDTH * scale) / 2f
        } else if (aspectRatio < Constants.ASPECT_RATIO) {
            scale = width.toFloat() / Constants.GAME_WIDTH.toFloat()
            crop.y = (height - Constants.GAME_HEIGHT * scale) / 2f
        } else {
            scale = width.toFloat() / Constants.GAME_WIDTH.toFloat()
        }

        w = Constants.GAME_WIDTH.toFloat() * scale
        h = Constants.GAME_HEIGHT.toFloat() * scale

        mViewPort = Rectangle(crop.x, crop.y, w, h)

        mHudStage.updateHudLayout(scale, crop, h)

        GLog.d(TAG, "RESIZE: " + scale)
        GLog.d(TAG, "RESIZE: " + crop)
        GLog.d(TAG, "RESIZE: " + h)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
        mGameStage.quitGame()
    }

    override fun dispose() {
        mHudStage.dispose()
        mGameStage.dispose()
    }

    companion object {

        private val TAG = GameScreen::class!!.simpleName
    }

}