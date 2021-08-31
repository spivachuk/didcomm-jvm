package org.dif.message

import com.nimbusds.jose.util.JSONObjectUtils
import org.dif.common.Typ

data class Message(
    val id: String,
    val body: Map<String, Any>,
    val type: String,
    val typ: Typ,
    val from: String?,
    val to: List<String>?,
    val createdTime: Int?,
    val expiresTime: Int?,
    val headers: Map<String, Any>?,
    val fromPrior: FromPrior?,
    val attachments: List<Attachment>?,
    val pleaseAck: Boolean?,
    val ack: String?,
    val thid: String?,
    val pthid: String?
) {
    private constructor(builder: Builder) : this(
        builder.id,
        builder.body,
        builder.type,
        builder.typ,
        builder.from,
        builder.to,
        builder.createdTime,
        builder.expiresTime,
        builder.headers,
        builder.fromPrior,
        builder.attachments,
        builder.pleaseAck,
        builder.ack,
        builder.thid,
        builder.pthid
    )

    companion object {
        fun builder(id: String, body: Map<String, Any>, type: String) = Builder(id, body, type)

        fun parse(json: Map<String, Any>): Message = builder(
            JSONObjectUtils.getString(json, "id"),
            JSONObjectUtils.getJSONObject(json, "body"),
            JSONObjectUtils.getString(json, "type")
        )
            .from(JSONObjectUtils.getString(json, "from"))
            .to(JSONObjectUtils.getStringList(json, "to"))
            .createdTime(JSONObjectUtils.getInt(json, "created_time"))
            .expiresTime(JSONObjectUtils.getInt(json, "expires_time"))
            .build()
    }

    class Builder(val id: String, val body: Map<String, Any>, val type: String) {
        var typ: Typ = Typ.Plaintext

        var from: String? = null
            private set

        var to: List<String>? = null
            private set

        var createdTime: Int? = null
            private set

        var expiresTime: Int? = null
            private set

        var headers: Map<String, Any>? = null
            private set

        var attachments: List<Attachment>? = null
            private set

        var fromPrior: FromPrior? = null
            private set

        var pleaseAck: Boolean? = null
            private set

        var ack: String? = null
            private set

        var thid: String? = null
            private set

        var pthid: String? = null
            private set

        fun from(from: String) = apply { this.from = from }
        fun to(to: List<String>) = apply { this.to = to }
        fun createdTime(createdTime: Int) = apply { this.createdTime = createdTime }
        fun expiresTime(expiresTime: Int) = apply { this.expiresTime = expiresTime }
        fun headers(headers: Map<String, Any>) = apply { this.headers = headers }
        fun fromPrior(fromPrior: FromPrior) = apply { this.fromPrior = fromPrior }
        fun attachments(attachments: List<Attachment>) = apply { this.attachments = attachments }
        fun pleaseAck(pleaseAck: Boolean) = apply { this.pleaseAck = pleaseAck }
        fun ack(ack: String) = apply { this.ack = ack }
        fun thid(thid: String) = apply { this.thid = thid }
        fun pthid(pthid: String) = apply { this.pthid = pthid }

        fun build() = Message(this)
    }

    fun toJSONObject() = mapOf(
        "id" to id,
        "typ" to typ.typ,
        "type" to type,
        "from" to from,
        "to" to to,
        "created_time" to createdTime,
        "expires_time" to expiresTime,
        "body" to body,
        "attachments" to attachments,
        "from_prior" to fromPrior,
        "please_ack" to pleaseAck,
        "ack" to ack,
        "thid" to thid,
        "pthid" to pthid
    ).filterValues { it != null }

    override fun toString(): String =
        JSONObjectUtils.toJSONStringForWeb(toJSONObject())
}
