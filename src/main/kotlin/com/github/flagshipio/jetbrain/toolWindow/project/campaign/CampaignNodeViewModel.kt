package com.github.flagshipio.jetbrain.toolWindow.project.campaign

import com.github.flagshipio.jetbrain.dataClass.Campaign

class CampaignNodeViewModel(
    val campaign: Campaign,
) {
    val campaignName = campaign.name
    val campaignDescription = campaign.description
    val campaignStatus = campaign.status
    val campaignScheduler = campaign.scheduler
}
