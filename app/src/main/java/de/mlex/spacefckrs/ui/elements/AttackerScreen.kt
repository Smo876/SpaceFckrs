package de.mlex.spacefckrs.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.JustSpace
import de.mlex.spacefckrs.data.USO


@Composable
fun AttackerScreen2(aliens: State<List<USO>>, alienRows: Int, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(top = 40.dp, bottom = 10.dp, start = 24.dp, end = 24.dp),
        columns = GridCells.Fixed(5)
    ) {
        items(aliens.value) {
            when(it) {
                is Alien -> DrawAlien(it, modifier = Modifier.size(90.dp))
                is JustSpace -> Spacer(Modifier.size(90.dp))
            }
        }
    }
}

@Composable
fun AttackerScreen(aliens: State<List<USO>>, alienRows: Int, modifier: Modifier = Modifier) {
    Row {
        AttackColumn(aliens, alienRows, 1)
        AttackColumn(aliens, alienRows, 2)
        AttackColumn(aliens, alienRows, 3)
        AttackColumn(aliens, alienRows, 4)
        AttackColumn(aliens, alienRows, 5)
    }
}

@Composable
fun AttackColumn(aliens: State<List<USO>>, alienRows: Int, row: Int) {
    Column {
        for (i in 1..alienRows) {
            aliens.value.forEach {
                when(it) {
                    is Alien -> DrawAlien(it, modifier = Modifier.size(90.dp))
                    is JustSpace -> Spacer(Modifier.size(90.dp))
                }
            }
        }
    }
}