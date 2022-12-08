package day07

typealias NodeName = String

sealed class FileSystemNode(
    open val name: String,
    open val size: Int,
) {
    data class File(
        override val name: String,
        override val size: Int,
    ) : FileSystemNode(name, size)

    data class Directory(
        override val name: String,
        override var size: Int = 0,
        val parent: Directory?,
        val children: MutableMap<NodeName, FileSystemNode> = hashMapOf(),
    ) : FileSystemNode(name, size)
}