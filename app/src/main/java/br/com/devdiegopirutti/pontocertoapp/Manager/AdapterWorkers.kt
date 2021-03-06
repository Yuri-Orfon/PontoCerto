package br.com.devdiegopirutti.pontocertoapp.Manager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.devdiegopirutti.pontocertoapp.Model.UsersToGestor
import br.com.devdiegopirutti.pontocertoapp.R
import com.google.firebase.database.FirebaseDatabase


class AdapterWorkers(var arrayListBase: ArrayList<UsersToGestor>, var context: Context)
    : RecyclerView.Adapter<AdapterWorkers.ViewHolderColaboradores>() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderColaboradores {
         val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return ViewHolderColaboradores(view)
    }

    override fun getItemCount(): Int = arrayListBase.size

    override fun onBindViewHolder(holder: ViewHolderColaboradores, position: Int) {
        holder.bind(arrayListBase[position])
    }

    fun addAllUsers(arrayList: ArrayList<UsersToGestor>) {
        arrayListBase.addAll(arrayList)
        notifyDataSetChanged()

    }

    class ViewHolderColaboradores(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTxt: TextView? = itemView.findViewById(R.id.viewColaborador)

        fun bind(usersToGestor: UsersToGestor) {
            nameTxt?.text = usersToGestor.nome

            nameTxt?.setOnClickListener {
                itemView.context.startActivity(
                        Intent(it.context, HistoryManager::class.java)
                                .apply { putExtra("userId", usersToGestor.userId) }
                )
            }
        }
    }
}