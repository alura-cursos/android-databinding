package br.com.alura.ceep.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.alura.ceep.database.AppDatabase
import br.com.alura.ceep.database.dao.NotaDAO
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.repository.NotaRepository
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter
import br.com.alura.ceep.ui.viewmodel.AppViewModel
import br.com.alura.ceep.ui.viewmodel.FormularioNotaViewModel
import br.com.alura.ceep.ui.viewmodel.ListaNotasViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val NOME_DB = "ceep.db"
private const val NOME_DB_TESTE = "ceep-teste.db"

val dbTesteModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_DB_TESTE
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                CoroutineScope(IO).launch {
                    val imagens = listOf(
                        "https://cdn.pixabay.com/photo/2016/11/19/14/00/code-1839406_960_720.jpg",
                        "https://cdn.pixabay.com/photo/2016/02/19/11/19/computer-1209641_960_720.jpg",
                        "https://cdn.pixabay.com/photo/2016/11/30/20/58/programming-1873854_960_720.png",
                        "https://cdn.pixabay.com/photo/2016/03/27/18/54/technology-1283624_960_720.jpg",
                        "https://cdn.pixabay.com/photo/2016/11/29/11/39/computer-1869236_960_720.jpg",
                        "https://cdn.pixabay.com/photo/2015/09/17/17/25/code-944499_960_720.jpg",
                        "https://cdn.pixabay.com/photo/2015/02/24/02/05/website-647013_960_720.jpg",
                        "https://cdn.pixabay.com/photo/2017/06/23/10/48/code-2434271_960_720.jpg",
                        "https://cdn.pixabay.com/photo/2015/09/09/21/12/monitor-933392_960_720.jpg"
                    )
                    repeat(10) {
                        val dao: NotaDAO by inject()
                        dao.salva(
                            Nota(
                                titulo = "Título ${it + 1}",
                                descricao = "Descrição ${it + 1}",
                                favorita = it % 2 == 0,
                                imagemUrl = if (it % 3 == 0) imagens.shuffled()[0] else ""
                            )
                        )
                    }
                }
            }
        }).build()
    }
}

val dbModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_DB
        ).build()
    }
}

val uiModule = module {
    factory<ListaNotasAdapter> { ListaNotasAdapter(get()) {} }
}

val daoModule = module {
    single<NotaDAO> { get<AppDatabase>().notaDao }
}

val repositoryModule = module {
    single<NotaRepository> { NotaRepository(get()) }
}

val viewModelModule = module {
    viewModel<ListaNotasViewModel> { ListaNotasViewModel(get()) }
    viewModel<FormularioNotaViewModel> { FormularioNotaViewModel(get()) }
    viewModel<AppViewModel> { AppViewModel() }
}

val appModule = listOf(
    dbModule,
    uiModule,
    repositoryModule,
    viewModelModule,
    daoModule
)

val testeAppModule = listOf(
    dbTesteModule,
    uiModule,
    repositoryModule,
    viewModelModule,
    daoModule
)