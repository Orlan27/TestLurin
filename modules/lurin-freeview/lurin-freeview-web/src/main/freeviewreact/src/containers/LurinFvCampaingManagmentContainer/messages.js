/*
 * LurinFvCampaingManagmentContainer Messages
 *
 * This contains all the text for the LurinFvCampaingManagmentContainer component.
 */
import { defineMessages } from 'react-intl';

export default defineMessages({
  header: {
    id: 'app.containers.LurinFvCampaingManagmentContainer.header',
    defaultMessage: 'This is LurinFvCampaingManagmentContainer container !'
  },
  DELETE_CAMPAIGN_VALIDATION: '!Error! No se puede eliminar la campaña',
  DELETE_CAMPAIGN_SUCCESSFULL: '!Correcto! Campaña eliminada correctamente.',
  CREATE_CAMPAIGN_SUCCESSFULL: '!Correcto! Campaña creada exitosamente.',
  UPDATE_CAMPAIGN_SUCCESSFULL: '!Correcto! Campaña actualizado exitosamente.',
  CREATE_CAMPAIGN_FAILURE: '!Error! No se logro crear la campaña.',
  UPDATE_CAMPAIGN_FAILURE: '!Error! No se logro actualizar la campaña.',
  DELETE_CAMPAIGN_FAILURE: '!Error! No se logro eliminar la campaña.',

  DELETE_CAMPAIGN_VALIDATION_ID: 'campaign.action.validation.id',
  DELETE_CAMPAIGN_SUCCESSFULL_ID: 'campaign.action.delete.success',
  CREATE_CAMPAIGN_SUCCESSFULL_ID: 'campaign.action.create.success',
  UPDATE_CAMPAIGN_SUCCESSFULL_ID: 'campaign.action.update.success',
  CREATE_CAMPAIGN_FAILURE_ID: 'campaign.action.create.failure',
  UPDATE_CAMPAIGN_FAILURE_ID: 'campaign.action.update.failure',
  DELETE_CAMPAIGN_FAILURE_ID: 'campaign.action.delete.failure',

});
