package com.doitintl.blaster.deleter

import com.doitintl.blaster.shared.Constants.ID
import com.doitintl.blaster.shared.Constants.PROJECT
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.pubsub.v1.TopicName

class TopicDeleter : AbstractDeleter() {
    override val pathKeys: Array<String>
        get() = arrayOf(PROJECT, ID)


    override fun doDelete(p: Map<String, String>) {
        TopicAdminClient.create().use {
            val topicName = TopicName.of(p[PROJECT], p[ID])
            it.deleteTopic(topicName)
        }
    }
}