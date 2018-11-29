/*
 * LurinFvGlobalParameterContainer Messages
 *
 * This contains all the text for the LurinFvGlobalParameterContainer component.
 */
import { defineMessages } from 'react-intl';

export default defineMessages({
  header: {
    id: 'app.containers.LurinFvGlobalParameterContainer.header',
    defaultMessage: 'This is LurinFvGlobalParameterContainer container !'
  },
  DELETE_GLOBALPARAMETER_VALIDATION: '!Error! No se puede eliminar el parámetro',
  DELETE_GLOBALPARAMETER_SUCCESSFULL: '!Correcto! Parámetro eliminado ocorrectamente.',
  CREATE_GLOBALPARAMETER_SUCCESSFULL: '!Correcto! Parámetro creado exitosamente.',
  UPDATE_GLOBALPARAMETER_SUCCESSFULL: '!Correcto! Parámetro actualizado exitosamente.',
  CREATE_GLOBALPARAMETER_FAILURE: '!Error! No se logro crear el Parámetro.',
  UPDATE_GLOBALPARAMETER_FAILURE: '!Error! No se logro actualizar el Parámetro.',
  DELETE_GLOBALPARAMETER_FAILURE: '!Error! No se logro eliminar el Parámetro.',

  DELETE_GLOBALPARAMETER_VALIDATION_ID: 'globalParameter.action.validation.id',
  DELETE_GLOBALPARAMETER_SUCCESSFULL_ID: 'globalParameter.action.delete.success',
  CREATE_GLOBALPARAMETER_SUCCESSFULL_ID: 'globalParameter.action.create.success',
  UPDATE_GLOBALPARAMETER_SUCCESSFULL_ID: 'globalParameter.action.update.success',
  CREATE_GLOBALPARAMETER_FAILURE_ID: 'globalParameter.action.create.failure',
  UPDATE_GLOBALPARAMETER_FAILURE_ID: 'globalParameter.action.update.failure',
  DELETE_GLOBALPARAMETER_FAILURE_ID: 'globalParameter.action.delete.failure'

});
