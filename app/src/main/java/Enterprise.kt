
import com.google.gson.annotations.SerializedName

data class Enterprise(
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("email_enterprise")
    val emailEnterprise: Any,
    @SerializedName("enterprise_name")
    val enterpriseName: String,
    @SerializedName("enterprise_type")
    val enterpriseType: EnterpriseType,
    @SerializedName("facebook")
    val facebook: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("linkedin")
    val linkedin: Any,
    @SerializedName("own_enterprise")
    val ownEnterprise: Boolean,
    @SerializedName("phone")
    val phone: Any,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("share_price")
    val sharePrice: Double,
    @SerializedName("twitter")
    val twitter: Any,
    @SerializedName("value")
    val value: Int
)