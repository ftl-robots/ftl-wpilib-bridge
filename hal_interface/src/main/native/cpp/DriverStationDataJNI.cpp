#include <jni.h>

#include "MockData/DriverStationData.h"
#include "edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setEnabled
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setEnabled
  (JNIEnv *, jclass, jboolean value)
{
    HALSIM_SetDriverStationEnabled(value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setDsAttached
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setDsAttached
  (JNIEnv *, jclass, jboolean value)
{
    HALSIM_SetDriverStationDsAttached(value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setAutonomous
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setAutonomous
  (JNIEnv *, jclass, jboolean value)
{
    HALSIM_SetDriverStationAutonomous(value);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    notifyNewData
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_notifyNewData
  (JNIEnv *, jclass)
{
    HALSIM_NotifyDriverStationNewData();
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setJoystickAxes
 * Signature: (B[F)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setJoystickAxes
  (JNIEnv * env, jclass, jbyte handle, jfloatArray inAxes)
{
    HAL_JoystickAxes newAxes;
    float* axes = env->GetFloatArrayElements(inAxes, NULL);
    newAxes.count = env->GetArrayLength(inAxes);
    for (int i = 0; i < newAxes.count; i++) {
        newAxes.axes[i] = axes[i];
    }
    env->ReleaseFloatArrayElements(inAxes, axes, 0);
    HALSIM_SetJoystickAxes(handle, &newAxes);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setJoystickPOVs
 * Signature: (B[S)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setJoystickPOVs
  (JNIEnv * env, jclass, jbyte handle, jshortArray inPovs)
{
    HAL_JoystickPOVs newPov;
    int16_t* povs = env->GetShortArrayElements(inPovs, NULL);
    newPov.count = env->GetArrayLength(inPovs);
    for (int i = 0; i < newPov.count; i++) {
        newPov.povs[i] = povs[i];
    }
    env->ReleaseShortArrayElements(inPovs, povs, 0);
    HALSIM_SetJoystickPOVs(handle, &newPov);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI
 * Method:    setJoystickButtons
 * Signature: (BII)V
 */
JNIEXPORT void JNICALL Java_edu_wpi_first_hal_sim_mockdata_DriverStationDataJNI_setJoystickButtons
  (JNIEnv * env, jclass, jbyte joystickNum, jint buttons, jint count)
{
    if (count > 32) {
        count = 32;
    }
    HAL_JoystickButtons joystickButtons;
    joystickButtons.count = count;
    joystickButtons.buttons = buttons;
    HALSIM_SetJoystickButtons(joystickNum, &joystickButtons);
}