package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.lib.util.CrashTracker;
import frc.robot.loops.Looper;
import frc.robot.subsystems.PlateCenter;

/**
 * The main robot class, which instantiates all robot parts and helper classes
 * and initializes all loops. Some classes are already instantiated upon robot
 * startup; for those classes, the robot gets the instance as opposed to
 * creating a new object
 * 
 * After initializing all robot parts, the code sets up the autonomous and
 * teleoperated cycles and also code that runs periodically inside both
 * routines.
 * 
 * This is the nexus/converging point of the robot code and the best place to
 * start exploring.
 * 
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

    // Get subsystem instances
    private PlateCenter mPlate = PlateCenter.getInstance();

    // Create subsystem manager
   // private final SubsystemManager mSubsystemManager = new SubsystemManager( //TODO: make sure you go back and add these
   //         Arrays.asList(mPlate));



    private Looper mEnabledLooper = new Looper();

    private ControlBoardInterface mControlBoard = ControlBoard.getInstance();

   

  
    public Robot() {
        CrashTracker.logRobotConstruction();
    }

    public void zeroAllSensors() {
      //  mSubsystemManager.zeroSensors();
    }

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit() {
        try {
            CrashTracker.logRobotInit();

           // mSubsystemManager.registerEnabledLoops(mEnabledLooper);
            mPlate.registerEnabledLoops(mEnabledLooper);
            //mEnabledLooper.register(RobotStateEstimator.getInstance())

           

           

        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
        zeroAllSensors();
    }

    /**
     * Initializes the robot for the beginning of autonomous mode (set drivebase, intake and superstructure to correct
     * states). Then gets the correct auto mode from the AutoModeSelector
     * 
     * @see AutoModeSelector.java
     */
    @Override
    public void autonomousInit() {
        
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        allPeriodic();
    }

    /**
     * Initializes the robot for the beginning of teleop
     */
    @Override
    public void teleopInit() {
        try {
            CrashTracker.logTeleopInit();

            // Start loopers
            mEnabledLooper.start();
            //mPlate.setWantedState(PlateCenter.SystemState.HOMING);

           zeroAllSensors();
            
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
    }

    /**
     * This function is called periodically during operator control.
     * 
     * The code uses state machines to ensure that no matter what buttons the driver presses, the robot behaves in a
     * safe and consistent manner.
     * 
     * Based on driver input, the code sets a desired state for each subsystem. Each subsystem will constantly compare
     * its desired and actual states and act to bring the two closer.
     */
    @Override
    public void teleopPeriodic() {
        try {
            //double timestamp = Timer.getFPGATimestamp();
            // Drive base

           // boolean wants_aim_button = mControlBoard.getAimButton();
          
        if(mControlBoard.getHatchPanelAlignment()) mPlate.setWantedState(PlateCenter.SystemState.AUTOALIGNING);
        else if(mControlBoard.getHatchPanelCentering()) mPlate.setWantedState(PlateCenter.SystemState.CENTERING);
        else if(mControlBoard.getHatchPanelDeploy()) mPlate.setWantedState(PlateCenter.SystemState.DEPLOYINGPLATE);
        else if(mControlBoard.getPlateHome()) mPlate.setWantedState(PlateCenter.SystemState.HOMING);
            
        mPlate.jog(mControlBoard.getHatchPanelJog());

               
            

           

           allPeriodic();
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
    }

    @Override
    public void disabledInit() {
        try {
            CrashTracker.logDisabledInit();

           /* if (mAutoModeExecuter != null) {
                mAutoModeExecuter.stop();
            }
            mAutoModeExecuter = null;
            */
            mEnabledLooper.stop();

            // Call stop on all our Subsystems.
            //mSubsystemManager.stop();
            mPlate.stop();

            //mDrive.setOpenLoop(DriveSignal.NEUTRAL);

           

            // If are tuning, dump map so far.
          /*  if (Constants.kIsShooterTuning) {
                for (Map.Entry<InterpolatingDouble, InterpolatingDouble> entry : mTuningFlywheelMap.entrySet()) {
                    System.out.println("{" +
                            entry.getKey().value + ", " + entry.getValue().value + "},");
                }
            }*/
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
    }

    @Override
    public void disabledPeriodic() {
      /*  final double kVoltageThreshold = 0.15;
        if (mCheckLightButton.getAverageVoltage() < kVoltageThreshold) {
            mLED.setLEDOn();
        } else {
            mLED.setLEDOff();
        }*/

        //zeroAllSensors();
        allPeriodic();
    }

    @Override
    public void testInit() {
       
    }

    @Override
    public void testPeriodic() {
    }

    /**
     * Helper function that is called in all periodic functions
     */
    public void allPeriodic() {
        
        //mSubsystemManager.outputToSmartDashboard();
       // mSubsystemManager.writeToLog();
        //mEnabledLooper.outputToSmartDashboard();
       
        
    }
}