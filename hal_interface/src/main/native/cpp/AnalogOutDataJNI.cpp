
#include <iostream>

#include "MockData/AnalogOutData.h"
#include "FTLBridgeUtils/CallbackStore.h"
#include "edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI.h"

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI
 * Method:    resetData
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI_resetData
  (JNIEnv*, jclass, jint index)
{
    HALSIM_ResetAnalogOutData(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI
 * Method:    registerInitializedCallback
 * Signature: (ILjava/lang/Object;Z)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI_registerInitializedCallback
  (JNIEnv* env, jclass, jint index, jobject callback, jboolean initialNotify)
{
    FTLBridge::AllocateCallback(env, index, callback, initialNotify,
            &HALSIM_RegisterAnalogOutInitializedCallback);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI
 * Method:    getVoltage
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI_getVoltage
  (JNIEnv*, jclass, jint index)
{
    return HALSIM_GetAnalogOutVoltage(index);
}

/*
 * Class:     edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI
 * Method:    setVoltage
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL
Java_edu_wpi_first_hal_sim_mockdata_AnalogOutDataJNI_setVoltage
  (JNIEnv*, jclass, jint index, jdouble value)
{
    HALSIM_SetAnalogOutVoltage(index, value);
}