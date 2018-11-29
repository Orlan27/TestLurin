/*
 * LurinStorageManagmentContainer Messages
 *
 * This contains all the text for the LurinFvMaLurinStorageManagmentContainerintenanceZoneContainer component.
 */
import { defineMessages } from 'react-intl';

export default defineMessages({
  header: {
    id: 'app.containers.LurinStorageManagmentContainer.header',
    defaultMessage: 'This is LurinStorageManagmentContainer container !'
  },
  TEST_CONNECTION_SUCCESSFULL: '!Conexión Correcta! Se ha establecido la conexión de forma correcta.',
  TEST_CONNECTION_FAILURE: '!Conexión Fallida! No se logro establecer la conexión.',
  DELETE_STORAGE_VALIDATION: '!Error! No se puede eliminar el almacenamiento ya que se encuentra asociado a una operadora existente.',
  DELETE_STORAGE_SUCCESSFULL: '!Correcto! Almacenamiento eliminado correctamente.',
  CREATE_STORAGE_SUCCESSFULL: '!Correcto! Almacenamiento creado exitosamente.',
  UPDATE_STORAGE_SUCCESSFULL: '!Correcto! Almacenamiento actualizado exitosamente.',
  CREATE_STORAGE_FAILURE: '!Error! No se logro crear el Almacenamiento.',
  UPDATE_STORAGE_FAILURE: '!Error! No se logro actualizar el Almacenamiento.',
  DELETE_STORAGE_FAILURE: '!Error! No se logro eliminar el Almacenamiento.',

  TEST_CONNECTION_SUCCESSFULL_ID: 'storageManagment.action.test.success',
  TEST_CONNECTION_FAILURE_ID: 'storageManagment.action.test.failure',
  DELETE_STORAGE_VALIDATION_ID: 'storageManagment.action.validation.id',
  DELETE_STORAGE_SUCCESSFULL_ID: 'storageManagment.action.delete.success',
  CREATE_STORAGE_SUCCESSFULL_ID: 'storageManagment.action.create.success',
  UPDATE_STORAGE_SUCCESSFULL_ID: 'storageManagment.action.update.success',
  CREATE_STORAGE_FAILURE_ID: 'storageManagment.action.create.failure',
  UPDATE_STORAGE_FAILURE_ID: 'storageManagment.action.update.failure',
  DELETE_STORAGE_FAILURE_ID: 'storageManagment.action.delete.failure',
  DELETE_OPERATOR_FAILURE_0004_ID: 'storageManagment.action.delete.failure_0004',
});
