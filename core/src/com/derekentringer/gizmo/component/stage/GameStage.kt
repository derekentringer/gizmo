package com.derekentringer.gizmo.component.stage

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.Manifold
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.derekentringer.gizmo.component.actor.BaseActor
import com.derekentringer.gizmo.component.actor.boss.phantom.PhantomBossActor
import com.derekentringer.gizmo.component.actor.boss.phantom.interfaces.IPhantomBoss
import com.derekentringer.gizmo.component.actor.boss.phantom.interfaces.IPhantomBossAttack
import com.derekentringer.gizmo.component.actor.enemy.PhantomActor
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangAmethystActor
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangBloodStoneActor
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangEmeraldActor
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangWoodActor
import com.derekentringer.gizmo.component.actor.item.interfaces.IItems
import com.derekentringer.gizmo.component.actor.`object`.DropCrystalBlueActor
import com.derekentringer.gizmo.component.actor.`object`.DropHeartActor
import com.derekentringer.gizmo.component.actor.player.PlayerActor
import com.derekentringer.gizmo.component.actor.player.interfaces.IPlayer
import com.derekentringer.gizmo.component.actor.structure.destroyable.DestroyableBlockFallActor
import com.derekentringer.gizmo.component.actor.structure.door.DoorBlackActor
import com.derekentringer.gizmo.component.actor.structure.door.DoorBloodActor
import com.derekentringer.gizmo.component.actor.structure.door.DoorBronzeActor
import com.derekentringer.gizmo.component.actor.structure.door.DoorGoldActor
import com.derekentringer.gizmo.component.actor.structure.door.interfaces.IDoor
import com.derekentringer.gizmo.component.stage.interfaces.IGameStage
import com.derekentringer.gizmo.component.stage.interfaces.IHudStage
import com.derekentringer.gizmo.manager.CameraManager
import com.derekentringer.gizmo.manager.ContactManager
import com.derekentringer.gizmo.manager.DropManager
import com.derekentringer.gizmo.manager.LocalDataManager
import com.derekentringer.gizmo.manager.interfaces.IDropManager
import com.derekentringer.gizmo.model.BaseModel
import com.derekentringer.gizmo.model.body.DeleteBody
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel
import com.derekentringer.gizmo.model.enemy.PhantomModel
import com.derekentringer.gizmo.model.item.boomerang.BoomerangAmethystModel
import com.derekentringer.gizmo.model.item.boomerang.BoomerangBloodStoneModel
import com.derekentringer.gizmo.model.item.boomerang.BoomerangEmeraldModel
import com.derekentringer.gizmo.model.item.boomerang.BoomerangWoodModel
import com.derekentringer.gizmo.model.level.LevelModel
import com.derekentringer.gizmo.model.`object`.DropCrystalBlueModel
import com.derekentringer.gizmo.model.`object`.DropHeartModel
import com.derekentringer.gizmo.model.`object`.KeyModel
import com.derekentringer.gizmo.model.player.PlayerModel
import com.derekentringer.gizmo.model.structure.destroyable.BaseDestroyableModel
import com.derekentringer.gizmo.model.structure.destroyable.DestroyableBlockFallModel
import com.derekentringer.gizmo.model.structure.destroyable.interfaces.IDestroyable
import com.derekentringer.gizmo.model.structure.door.DoorType
import com.derekentringer.gizmo.settings.Constants
import com.derekentringer.gizmo.util.BlockUtils
import com.derekentringer.gizmo.util.GameLevelUtils
import com.derekentringer.gizmo.util.ItemUtils
import com.derekentringer.gizmo.util.WorldUtils
import com.derekentringer.gizmo.util.input.UserInput
import com.derekentringer.gizmo.util.log.GLog
import com.derekentringer.gizmo.util.map.MapParser
import com.derekentringer.gizmo.util.map.interfaces.IMapParser

import java.util.ArrayList

class GameStage : Stage(), IMapParser, IPlayer, IDropManager, IItems, IHudStage, IPhantomBoss, IPhantomBossAttack, IDoor, IDestroyable, ContactListener {

    private val listeners = ArrayList<IGameStage>()
    private val mDeleteBodies = ArrayList<DeleteBody>()

    private val mCameraManager = CameraManager()
    private val mDropManager = DropManager()

    private var mWorld: World? = null
    private var mMapParser: MapParser? = null
    private var mSpriteBatch: SpriteBatch? = null

    private var mPlayerActor: PlayerActor? = null
    private var mPlayerModel: PlayerModel? = null
    private var mIsPlayerDead = false

    private var mLevelModel: LevelModel = LevelModel();
    private var mLoadedLevelModel: LevelModel? = null

    private var mDoorGoldActor: DoorGoldActor? = null
    private var mDoorBronzeActor: DoorBronzeActor? = null
    private var mDoorBloodActor: DoorBloodActor? = null
    private var mDoorBlackActor: DoorBlackActor? = null
    private var alreadyEntered = false

    fun addListener(listener: IGameStage) {
        listeners.add(listener)
    }

    fun init(level: LevelModel) {
        mLevelModel = level
        setupWorld()
        loadLevel(level, DoorType.DOOR_PREVIOUS)
        mCameraManager.createGameCameras()
        mDropManager.addListener(this)
    }

    private fun setupWorld() {
        mSpriteBatch = SpriteBatch()
        mWorld = WorldUtils.createWorld()
        mWorld!!.setContactListener(this)
    }

    fun loadLevel(level: LevelModel, whichDoor: String) {
        GLog.d(TAG, "loading level: " + level.levelInt)
        if (LocalDataManager.loadLevelData(level) != null) {
            mLoadedLevelModel = LocalDataManager.loadLevelData(level)
        } else {
            mLoadedLevelModel = level
        }

        mMapParser = MapParser(this, mLoadedLevelModel, level.levelMap, level.levelMidMap, level.levelBackMap)
        mMapParser!!.addListener(this)
        mMapParser!!.createTileMapLayers(mWorld)
        mMapParser!!.createTileMapObjects(mWorld, whichDoor)
    }

    override fun beginContact(contact: Contact) {
        val fixtureA = contact.fixtureA
        val fixtureB = contact.fixtureB
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body

        ContactManager.setPlayerOnGround(mPlayerActor, fixtureA, fixtureB)
        ContactManager.setPlayerAtDoor(mPlayerActor, bodyA, bodyB)
        ContactManager.setPlayerTouchingDestroyable(mPlayerActor, fixtureA, fixtureB)
        ContactManager.setPlayerAttacking(mMapParser, mDeleteBodies, bodyA, bodyB)
        ContactManager.setPlayerEnemyCollision(mPlayerActor, bodyA, bodyB)
        ContactManager.setPlayerPickupKey(mPlayerActor, mLoadedLevelModel, mDeleteBodies, bodyA, bodyB)
        ContactManager.setPlayerPickupHeart(mPlayerActor, mLoadedLevelModel, mDeleteBodies, listeners, bodyA, bodyB)
        ContactManager.setPlayerPickupLife(mPlayerActor, mLoadedLevelModel, mDeleteBodies, listeners, bodyA, bodyB)
        ContactManager.setPlayerPickupItem(mPlayerActor, mLoadedLevelModel, mDeleteBodies, bodyA, bodyB)
        ContactManager.setPlayerPickupSmallHeart(this, mPlayerActor, mDeleteBodies, bodyA, bodyB)
        ContactManager.setPlayerPickupSmallCrystalBlue(this, mPlayerActor, mDeleteBodies, bodyA, bodyB)
    }

    override fun endContact(contact: Contact) {
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {
    }

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {
    }

    override fun draw() {
        super.draw()

        // create dropped item actors
        addDroppedItems()

        // handle removed actors
        updateMapParserArrays()
        deleteObsoleteActors()

        //tiled maps render camera updates
        mMapParser!!.tiledMapBackgroundRenderer.setView(mCameraManager.backgroundCamera)
        mMapParser!!.tiledMapBackgroundRenderer.render()

        mMapParser!!.tiledMapMidBackgroundRenderer.setView(mCameraManager.midBackgroundCamera)
        mMapParser!!.tiledMapMidBackgroundRenderer.render()

        mMapParser!!.tiledMapRenderer.setView(mCameraManager.mainCamera)
        mMapParser!!.tiledMapRenderer.render()

        if (Constants.IS_DEBUG_BOX2D) {
            mCameraManager.box2dDebugRenderer.render(mWorld, mCameraManager.debugRendererCamera.combined)
        }

        mSpriteBatch!!.projectionMatrix = mCameraManager.mainCamera.combined

        // actor render loop
        // TODO make better?? don't let this get out of control
        for (actor in mMapParser!!.actorsArray) {
            actor.render(mSpriteBatch)
            if (actor.name.equals(PhantomModel.PHANTOM)) {
                (actor as PhantomActor).playerPosition = mPlayerActor!!.position
            } else if (actor.name.equals(PhantomLargeModel.PHANTOM_LARGE)) {
                (actor as PhantomBossActor).playerPosition = mPlayerActor!!.position
            } else if (actor.name.equals(BoomerangWoodModel.BOOMERANG_WOOD)) {
                (actor as BoomerangWoodActor).playerPosition = mPlayerActor!!.position
            } else if (actor.name.equals(BoomerangEmeraldModel.BOOMERANG_EMERALD)) {
                (actor as BoomerangEmeraldActor).playerPosition = mPlayerActor!!.position
            } else if (actor.name.equals(BoomerangAmethystModel.BOOMERANG_AMETHYST)) {
                (actor as BoomerangAmethystActor).playerPosition = mPlayerActor!!.position
            } else if (actor.name.equals(BoomerangBloodStoneModel.BOOMERANG_BLOODSTONE)) {
                (actor as BoomerangBloodStoneActor).playerPosition = mPlayerActor!!.position
            } else if (actor.name.equals(DropHeartModel.HEART_SMALL)) {
                (actor as DropHeartActor).playerPosition = mPlayerActor!!.position
            } else if (actor.name.equals(DropCrystalBlueModel.CRYSTAL_BLUE)) {
                (actor as DropCrystalBlueActor).playerPosition = mPlayerActor!!.position
            } else if (actor.name.equals(DestroyableBlockFallModel.DESTROYABLE_BLOCK_FALL)) {
                (actor as DestroyableBlockFallActor).playerPosition = mPlayerActor!!.position
            }
        }

        // checks for the player position
        handlePlayerOffMap(mPlayerActor!!.position.y)
        handlePlayerDied()
    }

    override fun act(delta: Float) {
        super.act(delta)

        // input
        UserInput.update()
        handleInput()

        // check to shake camera
        if (!mCameraManager.shakeCamera) {
            mCameraManager.updateCameraPlayerMovement(mPlayerActor!!.position.x, mPlayerActor!!.position.y, mMapParser)
        } else {
            mCameraManager.updateCameraPlayerMovement(WorldUtils.generatRandomPositiveNegativeValue(mPlayerActor!!.position.x, -mPlayerActor!!.position.x), WorldUtils.generatRandomPositiveNegativeValue(mPlayerActor!!.position.y, -mPlayerActor!!.position.y), mMapParser)
        }

        // actor loops
        for (actor in mMapParser!!.actorsArray) {
            actor.update(delta)
            actor.act(delta)
        }

        // game loop step
        Constants.ACCUMULATOR += delta
        while (Constants.ACCUMULATOR >= delta) {
            mWorld!!.step(Constants.TIME_STEP, 6, 2)
            Constants.ACCUMULATOR -= Constants.TIME_STEP
        }
    }

    override fun dispose() {
    }

    fun quitGame() {
        LocalDataManager.savePlayerActorData(mPlayerActor!!.baseModel)
        LocalDataManager.saveLevelData(mLoadedLevelModel)
    }

    private fun addDroppedItems() {
        for (i in 0..mMapParser!!.droppedItemPositionArray.size - 1) {
            mDropManager.randomDrop(mWorld, mMapParser!!.droppedItemPositionArray[i])
        }
        for (i in 0..mMapParser!!.bossDroppedItemPositionArray.size - 1) {
            mDropManager.bossDropCrystals(mWorld, mMapParser!!.bossDroppedItemPositionArray[i])
        }
        mMapParser!!.resetDroppedItemPositionArray()
        mMapParser!!.resetBossDroppedItemPositionArray()
    }

    private fun deleteObsoleteActors() {
        for (i in 0..mDeleteBodies.size - 1) {
            //look thru delete Bodies arraylist
            //delete the associated mBody
            //delete the actor from our actorsArray
            for (e in 0..mMapParser!!.actorsArray.size - 1) {
                val actorToDelete = mMapParser!!.actorsArray[e]
                if (actorToDelete.baseModel == mDeleteBodies[i].baseModel) {

                    GLog.d(TAG, "DELETING OBSOLETE ACTOR: " + mMapParser!!.actorsArray[e])

                    mMapParser!!.actorsArray.removeAt(e)
                    actorToDelete.remove()
                    //delete the mBody
                    WorldUtils.destroyBody(mWorld, mDeleteBodies[i].body)
                    mDeleteBodies.removeAt(i)
                    break
                }
            }
        }

        // remove any actor from actor array that
        // falls off the stage or goes out of bounds
        for (j in 0..mMapParser!!.actorsArray.size - 1) {
            if (mMapParser!!.actorsArray[j].position.y * Constants.PPM < 0 || mMapParser!!.actorsArray[j].position.x * Constants.PPM < 0) {

                GLog.d(TAG, "ACTOR OFF STAGE " + mMapParser!!.actorsArray[j])

                mMapParser!!.actorsArray[j].remove()
                mMapParser!!.actorsArray.removeAt(j)
            }
        }
    }

    private fun updateMapParserArrays() {
        for (i in 0..mMapParser!!.tempActorsArray.size - 1) {
            mMapParser!!.addToActorsArray(mMapParser!!.tempActorsArray[i])
        }
        mMapParser!!.tempActorsArray.clear()
    }

    private fun handlePlayerOffMap(playerY: Float) {
        if (playerY * Constants.PPM < 0) {
            playerIsOffMap(true)
        }
    }

    private fun handlePlayerDied() {
        if (mIsPlayerDead) {
            mIsPlayerDead = false
            mMapParser!!.destroyTiledMap()
            WorldUtils.destroyBodies(mWorld)
            //TODO could load first room of multiple rooms
            loadLevel(mLevelModel, DoorType.DOOR_PREVIOUS)
        }
    }

    private fun handleInput() {
        // walk left
        if (UserInput.isDown(UserInput.LEFT_BUTTON) && !UserInput.isDown(UserInput.RUN)) {
            alreadyEntered = false
            mPlayerActor!!.moveLeft(false)
        }

        // run left
        if (UserInput.isDown(UserInput.LEFT_BUTTON) && UserInput.isDown(UserInput.RUN)) {
            alreadyEntered = false
            mPlayerActor!!.moveLeft(true)
        }

        // walk right
        if (UserInput.isDown(UserInput.RIGHT_BUTTON) && !UserInput.isDown(UserInput.RUN)) {
            alreadyEntered = false
            mPlayerActor!!.moveRight(false)
        }

        // run right
        if (UserInput.isDown(UserInput.RIGHT_BUTTON) && UserInput.isDown(UserInput.RUN)) {
            alreadyEntered = false
            mPlayerActor!!.moveRight(true)
        }

        // stop running/walking
        if (!UserInput.isDown(UserInput.LEFT_BUTTON) && !UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            mPlayerActor!!.stoppedMoving()
        }

        // jump
        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            if (mPlayerActor!!.isOnGround) {
                mPlayerActor!!.isOnGround = false
                mPlayerActor!!.jump()
            }
        }

        // stop jumping
        if (!UserInput.isDown(UserInput.JUMP_BUTTON)) {
            mPlayerActor!!.stopJumping()
        }

        // dig
        if (UserInput.isDown(UserInput.DIG_BUTTON)) {
            if (mPlayerActor!!.isOnGround) {
                mPlayerActor!!.dig()
                if (UserInput.isDown(UserInput.RIGHT_BUTTON) && mPlayerActor!!.touchingBodyDestroyableRight != null) {
                    BlockUtils.setBlockHealth(mPlayerActor!!.touchingBodyDestroyableRight, mPlayerModel!!.diggingPower)
                    if (BlockUtils.getBlockHealth(mPlayerActor!!.touchingBodyDestroyableRight) <= 0) {
                        if (BlockUtils.getBlockDropsLoot(mPlayerActor!!.touchingBodyDestroyableRight)) {
                            mMapParser!!.addToDroppedItemPositionArray(mPlayerActor!!.touchingBodyDestroyableRight.position)
                        }
                        mDeleteBodies.add(DeleteBody(mPlayerActor!!.touchingBodyDestroyableRight.userData as BaseDestroyableModel, mPlayerActor!!.touchingBodyDestroyableRight))
                        mLoadedLevelModel!!.addDestroyedBlock(mPlayerActor!!.touchingBodyDestroyableRight.userData as BaseDestroyableModel)
                        mPlayerActor!!.touchingBodyDestroyableRight = null
                    }
                } else if (UserInput.isDown(UserInput.LEFT_BUTTON) && mPlayerActor!!.touchingBodyDestroyableLeft != null) {
                    BlockUtils.setBlockHealth(mPlayerActor!!.touchingBodyDestroyableLeft, mPlayerModel!!.diggingPower)
                    if (BlockUtils.getBlockHealth(mPlayerActor!!.touchingBodyDestroyableLeft) <= 0) {
                        if (BlockUtils.getBlockDropsLoot(mPlayerActor!!.touchingBodyDestroyableLeft)) {
                            mMapParser!!.addToDroppedItemPositionArray(mPlayerActor!!.touchingBodyDestroyableLeft.position)
                        }
                        mDeleteBodies.add(DeleteBody(mPlayerActor!!.touchingBodyDestroyableLeft.userData as BaseDestroyableModel, mPlayerActor!!.touchingBodyDestroyableLeft))
                        mLoadedLevelModel!!.addDestroyedBlock(mPlayerActor!!.touchingBodyDestroyableLeft.userData as BaseDestroyableModel)
                        mPlayerActor!!.touchingBodyDestroyableLeft = null
                    }
                } else if (mPlayerActor!!.touchingBodyDestroyableBottom != null) {
                    BlockUtils.setBlockHealth(mPlayerActor!!.touchingBodyDestroyableBottom, mPlayerModel!!.diggingPower)
                    if (BlockUtils.getBlockHealth(mPlayerActor!!.touchingBodyDestroyableBottom) <= 0) {
                        if (BlockUtils.getBlockDropsLoot(mPlayerActor!!.touchingBodyDestroyableBottom)) {
                            mMapParser!!.addToDroppedItemPositionArray(mPlayerActor!!.touchingBodyDestroyableBottom.position)
                        }
                        mDeleteBodies.add(DeleteBody(mPlayerActor!!.touchingBodyDestroyableBottom.userData as BaseDestroyableModel, mPlayerActor!!.touchingBodyDestroyableBottom))
                        mLoadedLevelModel!!.addDestroyedBlock(mPlayerActor!!.touchingBodyDestroyableBottom.userData as BaseDestroyableModel)
                        mPlayerActor!!.touchingBodyDestroyableBottom = null
                    }
                }
            }
        }

        // enter a door
        if (UserInput.isDown(UserInput.ENTER_DOOR)) {
            if (alreadyEntered) {
                return
            }
            if (mPlayerActor!!.isAtDoor) {
                if (mPlayerActor!!.isAtDoorUserData.doorType == DoorType.DOOR_LOCKED_GOLD) {
                    if (mPlayerActor!!.hasCorrectKey(KeyModel.KEY_GOLD) || !mPlayerActor!!.isAtDoorUserData.isLocked) {
                        mDoorGoldActor!!.startAnimation()
                    }
                } else if (mPlayerActor!!.isAtDoorUserData.doorType == DoorType.DOOR_LOCKED_BRONZE) {
                    if (mPlayerActor!!.hasCorrectKey(KeyModel.KEY_BRONZE) || !mPlayerActor!!.isAtDoorUserData.isLocked) {
                        mDoorBronzeActor!!.startAnimation()
                    }
                } else if (mPlayerActor!!.isAtDoorUserData.doorType == DoorType.DOOR_LOCKED_BLOOD) {
                    if (mPlayerActor!!.hasCorrectKey(KeyModel.KEY_BLOOD) || !mPlayerActor!!.isAtDoorUserData.isLocked) {
                        mDoorBloodActor!!.startAnimation()
                    }
                } else if (mPlayerActor!!.isAtDoorUserData.doorType == DoorType.DOOR_LOCKED_BLACK) {
                    if (mPlayerActor!!.hasCorrectKey(KeyModel.KEY_BLACK) || !mPlayerActor!!.isAtDoorUserData.isLocked) {
                        mDoorBlackActor!!.startAnimation()
                    }
                } else if (mPlayerActor!!.isAtDoorUserData.doorType == DoorType.DOOR_OTHER) {
                    transitionLevel(DoorType.DOOR_OTHER)
                } else if (mPlayerActor!!.isAtDoorUserData.doorType == DoorType.DOOR_PREVIOUS) {
                    if (mLevelModel.levelInt > 0) {
                        transitionLevel(DoorType.DOOR_PREVIOUS)
                    }
                } else if (mPlayerActor!!.isAtDoorUserData.doorType == DoorType.DOOR_NEXT) {
                    if (mLevelModel.levelInt < GameLevelUtils.gameLevels.size - 1) {
                        transitionLevel(DoorType.DOOR_NEXT)
                    }
                }
            }
        }

        // attack & kill stuff
        if (UserInput.isDown(UserInput.ATTACK)) {
            // TODO check current item
            // TODO this will require a inventory system
            val playerBestBoomerang = mPlayerActor!!.playerBestBoomerang
            if (playerBestBoomerang != null) {
                if (!mPlayerActor!!.isItemActive) {
                    mPlayerActor!!.isItemActive = true
                    if (playerBestBoomerang.equals(BoomerangWoodModel.BOOMERANG_WOOD)) {
                        ItemUtils.createWoodBoomerang(mWorld, mPlayerActor, mMapParser, this)
                    } else if (playerBestBoomerang.equals(BoomerangEmeraldModel.BOOMERANG_EMERALD)) {
                        ItemUtils.createEmeraldBoomerang(mWorld, mPlayerActor, mMapParser, this)
                    } else if (playerBestBoomerang.equals(BoomerangAmethystModel.BOOMERANG_AMETHYST)) {
                        ItemUtils.createAmethystBoomerang(mWorld, mPlayerActor, mMapParser, this)
                    } else if (playerBestBoomerang.equals(BoomerangBloodStoneModel.BOOMERANG_BLOODSTONE)) {
                        ItemUtils.createBloodStoneBoomerang(mWorld, mPlayerActor, mMapParser, this)
                    }
                }
            }
        }
    }

    private fun transitionLevel(doorType: String) {
        for (listener in listeners) {
            listener.setTransition(doorType, true)
        }
    }

    private fun loadNewLevel(newLevel: Int, whichDoor: String) {
        alreadyEntered = true
        mPlayerActor!!.isItemActive = false

        mLevelModel = GameLevelUtils.gameLevels[newLevel]

        mMapParser!!.destroyTiledMap()
        WorldUtils.destroyBodies(mWorld)

        mPlayerActor!!.setCurrentLevel(newLevel)

        LocalDataManager.savePlayerActorData(mPlayerActor!!.baseModel)
        LocalDataManager.saveLevelData(mLoadedLevelModel)

        loadLevel(GameLevelUtils.gameLevels[newLevel], whichDoor)
    }

    override fun setPlayerActor(playerActor: PlayerActor) {
        mPlayerActor = playerActor
        mPlayerActor!!.addListener(this)
        mPlayerActor!!.isItemActive = false
        if (LocalDataManager.loadPlayerActorData() != null) {
            mPlayerModel = LocalDataManager.loadPlayerActorData()
            playerActor.initPlayerData(mPlayerModel)
        } else {
            mPlayerModel = PlayerModel()
            mPlayerModel!!.hearts = PlayerModel.DEFAULT_HEARTS
            mPlayerModel!!.health = PlayerModel.DEFAULT_HEALTH
            mPlayerModel!!.lives = PlayerModel.DEFAULT_LIVES
            mPlayerModel!!.currentLevel = PlayerModel.DEFAULT_LEVEL
            mPlayerModel!!.diggingPower = PlayerModel.DEFAULT_DIGGING_POWER
            playerActor.initPlayerData(mPlayerModel)
            LocalDataManager.savePlayerActorData(mPlayerModel)
        }

        updateHud()
    }

    fun updateHud() {
        for (listener in listeners) {
            listener.setHudHealthHearts(mPlayerActor!!.baseModel.hearts)
            listener.resetHudShapes()
            listener.setHudHealth(mPlayerActor!!.baseModel.health)
            listener.setHudLives(mPlayerActor!!.baseModel.lives)
        }
    }

    override fun setLockedGoldDoor(doorGoldActor: DoorGoldActor) {
        mDoorGoldActor = doorGoldActor
        mDoorGoldActor!!.addListener(this)
    }

    override fun setLockedBronzeDoor(bronzeDoorActor: DoorBronzeActor) {
        mDoorBronzeActor = bronzeDoorActor
        mDoorBronzeActor!!.addListener(this)
    }

    override fun setLockedBloodDoor(bloodDoorActor: DoorBloodActor) {
        mDoorBloodActor = bloodDoorActor
        mDoorBloodActor!!.addListener(this)
    }

    override fun setLockedBlackDoor(blackDoorActor: DoorBlackActor) {
        mDoorBlackActor = blackDoorActor
        mDoorBlackActor!!.addListener(this)
    }

    override fun playerIsOffMap(offMap: Boolean) {
        killPlayer()
    }

    override fun playerGotHit(playerHealth: Int) {
        for (listener in listeners) {
            listener.setHudHealth(playerHealth)
        }
        if (playerHealth <= 0) {
            killPlayer()
        }
    }

    override fun playerZeroLives() {
        //show died screen
        mPlayerActor!!.resetLives()
    }

    private fun killPlayer() {
        mPlayerActor!!.resetHealth()
        mPlayerActor!!.deIncrementLives()
        for (listener in listeners) {
            listener.setHudLives(mPlayerActor!!.baseModel.lives)
        }
        mCameraManager.shakeCamera = false
        LocalDataManager.savePlayerActorData(mPlayerActor!!.baseModel)
        mIsPlayerDead = true
    }

    override fun phantomBossShakeCamera(shake: Boolean) {
        mCameraManager.shakeCamera = shake
    }

    override fun phantomBossAddActor(actor: BaseActor) {
        mMapParser!!.addToTempActorsArray(actor)
    }

    override fun doorAnimationComplete(actor: BaseActor) {
        actor.isPlayingAnimation = false
        transitionLevel(DoorType.DOOR_LOCKED)
    }

    override fun hudFadeInComplete(doorType: String) {
        if (doorType == DoorType.DOOR_OTHER) {
            loadNewLevel(mPlayerActor!!.isAtDoorUserData.levelNumber, mPlayerActor!!.isAtDoorUserData.destinationDoor)
        } else if (doorType == DoorType.DOOR_PREVIOUS) {
            loadNewLevel(mLevelModel!!.levelInt - 1, DoorType.DOOR_NEXT)
        } else if (doorType == DoorType.DOOR_NEXT) {
            loadNewLevel(mLevelModel!!.levelInt + 1, DoorType.DOOR_PREVIOUS)
        } else {
            mLoadedLevelModel!!.addOpenedDoor(mPlayerActor!!.isAtDoorUserData)
            loadNewLevel(mPlayerActor!!.isAtDoorUserData.levelNumber, mPlayerActor!!.isAtDoorUserData.destinationDoor)
        }
    }

    override fun removePlayerItemFromStage(actor: BaseActor) {
        mPlayerActor!!.isItemActive = false
        mDeleteBodies.add(DeleteBody(actor.body.userData as BaseModel, actor.body))
    }

    override fun addDroppedItem(actor: BaseActor) {
        mMapParser!!.addToTempActorsArray(actor)
    }

    override fun removeBlockFromMap(baseActor: BaseActor) {
        mLoadedLevelModel!!.addDestroyedBlock(baseActor.body.userData as BaseDestroyableModel)
    }

    companion object {

        private val TAG = GameStage::class!!.simpleName
    }

    /*private void startBackgroundMusic() {
        Music backgroundMusic = Gizmo.assetManager.get("res/music/background.ogg", Music.class);
        if(!Constants.IS_DEBUGGING) {
            backgroundMusic.play();
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
        }
    }*/

}