
import com.google.gson.annotations.SerializedName

data class EnterpriseType(
    @SerializedName("enterprise_type_name")
    val enterpriseTypeName: String,
    @SerializedName("id")
    val id: Int
)