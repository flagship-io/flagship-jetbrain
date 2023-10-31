package com.github.flagshipio.jetbrain.toolWindow.project.campaign

import com.github.flagshipio.jetbrain.dataClass.Campaign

class CampaignNodeViewModel(
    val campaign: Campaign,
) {
    val campaignName = campaign.name
    val campaignId = campaign.id
    val campaignType = campaign.type
    val campaignDescription = campaign.description
    val campaignProjectID = campaign.projectID
    val campaignStatus = campaign.status
    val campaignVariationGroups = campaign.variationGroups
    val campaignScheduler = campaign.scheduler
}
