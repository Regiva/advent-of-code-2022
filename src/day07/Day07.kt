package day07

import readLines

fun main() {

    val id = "07"
    val testOutput = 95437

    fun readTerminalOutput(fileName: String): List<String> {
        return readLines(fileName)
    }

    fun createFileSystemTree(input: List<String>): FileSystemNode.Directory {
        val rootNode = FileSystemNode.Directory(
            name = "/",
            parent = null,
        )
        var currentNode = rootNode

        input.forEach { inputLine ->
            val line = inputLine.split(" ")
            if (line.first() == "$") {
                when (line[1]) {
                    "ls" -> return@forEach

                    "cd" -> {
                        currentNode = when (val destinationName = line[2]) {
                            "/" -> rootNode
                            ".." -> currentNode.parent!!
                            else -> {
                                val destinationNode = currentNode.children[destinationName]
                                destinationNode as FileSystemNode.Directory
                            }
                        }
                    }
                }
            } else {
                val destinationName = line[1]
                currentNode.children[destinationName] = when (line.first()) {
                    "dir" -> {
                        FileSystemNode.Directory(
                            name = destinationName,
                            parent = currentNode,
                        )
                    }

                    else -> {
                        val size = line[0].toInt()
                        currentNode.updateSize(size)

                        FileSystemNode.File(
                            name = destinationName,
                            size = size,
                        )
                    }
                }
            }
        }

        return rootNode
    }

    // Time — O(), Memory — O()
    fun part1(input: FileSystemNode.Directory): Int {
        val sizeAtMost = 100000
        var deleteDirsSize = 0

        input.children.forEach { node ->
            deleteDirsSize += (node.value as? FileSystemNode.Directory)
                ?.findDirsTotalSizeBySize(sizeAtMost) ?: 0
        }

        return deleteDirsSize
    }

    // Time — O(), Memory — O()
    fun part2(input: FileSystemNode.Directory): Int {
        val totalSpace = 70000000
        val updateSpace = 30000000
        val usedSpace = input.size
        val neededSpace = updateSpace - (totalSpace - usedSpace)

        val dirsToDeleteSizes = mutableListOf(Int.MAX_VALUE)
        input.children.forEach { node ->
            (node.value as? FileSystemNode.Directory)?.let {
                dirsToDeleteSizes.add(it.findDirToDeleteSize(neededSpace))
            }
        }
        return dirsToDeleteSizes.min()
    }

    val testInput = createFileSystemTree(readTerminalOutput("day$id/Day${id}_test"))
    println(part1(testInput))
    check(part1(testInput) == testOutput)

    val input = createFileSystemTree(readTerminalOutput("day$id/Day$id"))
    println(part1(input))
    println(part2(input))
}

fun FileSystemNode.Directory.updateSize(size: Int) {
    this.size += size
    parent?.apply { updateSize(size) }
}

fun FileSystemNode.Directory.findDirsTotalSizeBySize(size: Int): Int {
    var deleteDirsSize = 0
    if (this.size < size) deleteDirsSize = this.size

    children.forEach { node ->
        if (node.value is FileSystemNode.Directory) {
            deleteDirsSize += (node.value as FileSystemNode.Directory).findDirsTotalSizeBySize(size)
        }
    }
    return deleteDirsSize
}

fun FileSystemNode.Directory.findDirToDeleteSize(neededSpace: Int): Int {
    val dirsToDeleteSizes = mutableListOf(Int.MAX_VALUE)
    if (this.size > neededSpace) dirsToDeleteSizes.add(this.size)

    children.forEach { node ->
        if (node.value is FileSystemNode.Directory) {
            dirsToDeleteSizes.add(
                (node.value as FileSystemNode.Directory).findDirToDeleteSize(neededSpace)
            )
        }
    }

    return dirsToDeleteSizes.min()
}
