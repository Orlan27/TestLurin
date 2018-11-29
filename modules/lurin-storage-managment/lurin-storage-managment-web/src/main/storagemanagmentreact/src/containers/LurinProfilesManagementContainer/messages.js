/*
 * LurinProfilesManagementContainer Messages
 *
 * This contains all the text for the LurinProfilesManagementContainer component.
 */
import { defineMessages } from 'react-intl';

export default defineMessages({
  header: {
    id: 'app.containers.LurinProfilesManagementContainer.header',
    defaultMessage: 'This is LurinProfilesManagementContainer container !'
  },
  DELETE_PROFILE_VALIDATION: '!Error! No se puede eliminar el perfil',
  DELETE_PROFILE_SUCCESSFULL: '!Correcto! Perfil eliminado correctamente.',
  CREATE_PROFILE_SUCCESSFULL: '!Correcto! Perfil creado exitosamente.',
  UPDATE_PROFILE_SUCCESSFULL: '!Correcto! Perfil actualizado exitosamente.',
  CREATE_PROFILE_FAILURE: '!Error! No se logro crear el perfil.',
  UPDATE_PROFILE_FAILURE: '!Error! No se logro actualizar el perfil.',
  DELETE_PROFILE_FAILURE: '!Error! No se logro eliminar el perfil.',

  DELETE_PROFILE_VALIDATION_ID: 'profile.action.validation.id',
  DELETE_PROFILE_SUCCESSFULL_ID: 'profile.action.delete.success',
  CREATE_PROFILE_SUCCESSFULL_ID: 'profile.action.create.success',
  UPDATE_PROFILE_SUCCESSFULL_ID: 'profile.action.update.success',
  CREATE_PROFILE_FAILURE_ID: 'profile.action.create.failure',
  UPDATE_PROFILE_FAILURE_ID: 'profile.action.update.failure',
  DELETE_PROFILE_FAILURE_ID: 'profile.action.delete.failure'
});
