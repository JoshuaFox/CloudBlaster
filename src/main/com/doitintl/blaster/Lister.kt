package com.doitintl.blaster

import com.doitintl.blaster.shared.AssetIterator
import com.doitintl.blaster.shared.Callback
import com.doitintl.blaster.shared.Constants
import com.doitintl.blaster.shared.Constants.CLOUD_BLASTER
import picocli.CommandLine
import java.io.FileWriter
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = CLOUD_BLASTER,
    mixinStandardHelpOptions = true,
    description = ["Lists assets in a GCP project."]
)
class Lister : Callable<Any> {
    @CommandLine.Option(names = ["-p", "--project"], required = true)
    private lateinit var project: String

    @CommandLine.Option(names = ["-a", "--print-all-assets"])
    private var allAssetTypes = false


    override fun call(): Int {
        FileWritingCallback(Constants.LISTED_ASSETS_FILENAME).use { callback ->
            AssetIterator().listAssets(project, callback, allAssetTypes)
            return 0
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val exitCode = CommandLine(Lister()).execute(*args)
            exitProcess(exitCode)
        }
    }
}

internal class FileWritingCallback(filename: String) : Callback<String> {
    private val fw: FileWriter = FileWriter(filename)

    override fun call(s: String) {
        fw.write("$s\n")
    }

    override fun close() {
        fw.close()
    }


}