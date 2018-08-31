package br.com.vitorcesario.githubsearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.Toast
import br.com.vitorcesario.githubsearch.api.GitHubService
import br.com.vitorcesario.githubsearch.model.Usuario
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btPesquisar.setOnClickListener {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val service = retrofit.create(GitHubService::class.java)
            service.buscarUsuario(etUsername.text.toString())
                    .enqueue(object : Callback<Usuario> {
                        override fun onFailure(call: Call<Usuario>?, t: Throwable?) {
                            Toast.makeText(this@MainActivity,
                                    "Faiô",
                                    Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Usuario>?, response: Response<Usuario>?) {
                            val usuario = response?.body()
                            if (response!!.isSuccessful) {
                                fillUser(usuario)
                            } else {
                                userNotFound()
                            }
                        }
                    })
        }
    }

    private fun fillUser(usuario: Usuario?) {
        Picasso.get()
                .load(usuario?.avatar_Url)
                .into(ivImagem)
        tvNome.text = usuario?.nome
    }

    private fun userNotFound() {
        tvNome.text = "ESSE MALUKO NÃO EXISTE"
        ivImagem.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.error))
    }
}
