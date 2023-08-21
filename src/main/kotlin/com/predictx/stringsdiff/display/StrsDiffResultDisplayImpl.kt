package com.predictx.stringsdiff.display

import com.predictx.stringsdiff.domain.StrCharInfo


class StrsDiffResultDisplayImpl(private val resultSeparator: String) : StrsDiffResultDisplay {

    override fun displayStringDiffResult(chars: List<StrCharInfo>): String {
        return chars.sortedWith(SortingCriteria.sortingCriteria).joinToString(separator = resultSeparator) { displayStrCharInfo(it) }
    }

    private fun displayStrCharInfo(charCount: StrCharInfo): String {
        val displayStyle = if (charCount.id == null) displayUnidentifiedCharCount else displayIdentifiedCharCount
        return displayStyle.invoke(charCount)
    }

    private companion object DisplayStyles {
        val displayIdentifiedCharCount = { charInfo: StrCharInfo -> charInfo.id + ":" + repeatChar(charInfo) }
        val displayUnidentifiedCharCount = { charInfo: StrCharInfo -> "=:" + repeatChar(charInfo) }

        private fun repeatChar(character: StrCharInfo) = character.char.toString().repeat(character.count)
    }

      /*
       * Sort by:  1. Descending count
       *           2. Descending "max string id presence"(not sure if this was intentional in problem sheet)
       *           3. Ascending alphabet
       *
       * All comparators contracts are kept intact! - no cheating of reverse signs! no composing everything into one comparator!
       * hence you see ".reversed" for 1. and "thenByDescending" for 2.
       *
       */
     object SortingCriteria {
        private val reverseCountComparator = Comparator<StrCharInfo> { chartInfoOne, charInfoTwo ->
            if (chartInfoOne.count > charInfoTwo.count) 1
            else if (chartInfoOne.count < charInfoTwo.count) -1
            else 0
        }.reversed()

        private val idPresenceComparator = Comparator<StrCharInfo> { chartInfoOne, charInfoTwo -> compareByIdPresence(chartInfoOne.id, charInfoTwo.id) }

        val sortingCriteria = reverseCountComparator.thenByDescending (idPresenceComparator){it}.thenBy {it.char}

        private fun compareByIdPresence(idOne: String?, idTwo: String?) =
            if (idOne.isNullOrBlank() && idTwo.isNullOrBlank()) 0
            else if (idOne != null && idTwo != null) 0
            else if (idOne != null) 1
            else -1

    }
}