package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.USO


@Composable
fun AttackerScreen(aliens: State<List<USO>>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(top = 40.dp, bottom = 10.dp, start = 24.dp, end = 24.dp),
        columns = GridCells.Fixed(5)
    ) {
        items(aliens.value.size) { it ->
            if (aliens.value[it] is Alien) {
                DrawAlien(
                    type = aliens.value[it].type,
                    life = aliens.value[it].life,
                    modifier = Modifier
                        .size(90.dp)
                )
            }
        }
    }
}

//@Composable
//fun AttackerScreen2(aliens: State<List<USO>>, modifier: Modifier = Modifier) {
//    Row {
//        AttackColumn()
//        AttackColumn()
//        AttackColumn()
//        AttackColumn()
//        AttackColumn()
//    }
//}
//
//@Composable
//fun AttackColumn() {
//    Column {
//        items(aliens.value.size) { it ->
//            if (aliens.value[it] is Alien) {
//                DrawAlien(
//                    type = aliens.value[it].type,
//                    life = aliens.value[it].life,
//                    modifier = Modifier
//                        .size(90.dp)
//                )
//            }
//        }
//    }
//}
