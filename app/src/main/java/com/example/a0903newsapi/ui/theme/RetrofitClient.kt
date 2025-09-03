import com.example.a0903newsapi.ui.theme.NaverNewsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInterface {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NaverNewsApi by lazy {
        retrofit.create(NaverNewsApi::class.java)
    }
}