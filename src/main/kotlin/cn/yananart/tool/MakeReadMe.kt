package cn.yananart.tool

import cn.hutool.core.io.FileUtil
import cn.hutool.core.io.resource.ResourceUtil
import cn.hutool.core.lang.Dict
import cn.hutool.core.util.SystemPropsUtil
import cn.hutool.extra.template.TemplateConfig
import cn.hutool.extra.template.TemplateUtil
import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import java.io.File


/**
 * log
 */
val log: Log = LogFactory.get()


/**
 * 问题类
 *
 * @author Yananart
 */
data class Problem(val title: String, var path: String)


/**
 * 竞赛类
 */
data class Contest(val title: String, var path: String)


/**
 * 获取markdown文档标题
 */
fun getTitle(markdown: File): String {
    val fileLine = FileUtil.readUtf8Lines(markdown).first()
    return fileLine.removePrefix("#").trim()
}


/**
 * 获取所有的问题
 */
fun getAllProblems(path: String): List<Problem> {
    val fileList = FileUtil.ls(path)
    fileList.sort()
    val problems = ArrayList<Problem>()
    // 遍历文件，暂时不考虑效率问题
    for (file in fileList) {
        val title = getTitle(file)
        val filePath = path + File.separator + file.name
        problems.add(Problem(title, filePath))
    }
    return problems
}


fun getAllContests(path: String): List<Contest> {
    val fileList = FileUtil.ls(path)
    fileList.sortBy { it.name }
    val contests = ArrayList<Contest>()
    for (file in fileList) {
        val fileName = file.name
        val filePart = fileName.split(".")
        val title = if (filePart[1].contains("biweekly")) {
            "第${filePart[1].split("-contest-")[1]}场双周赛 [${filePart[0]}]"
        } else {
            "第${filePart[1].split("-contest-")[1]}场周赛 [${filePart[0]}]"
        }
        contests.add(Contest(title, path + File.separator + file.name))
    }
    return contests
}


fun main() {
    log.info("开始生成readme.md ...")

    val workPath = SystemPropsUtil.get("user.dir")
    log.info("项目目录：${workPath}")

    log.info("读取文档目录，获取问题集...")
    val problems = getAllProblems("${workPath}/markdown/leetcode/problemset")
    log.info("读取文档目录，共解析获取[${problems.size}]个文档")
    problems.forEach {
        it.path = it.path.removePrefix(workPath + File.separator)
    }

    log.info("读取文档目录，获取竞赛集...")
    val contests = getAllContests("${workPath}/markdown/leetcode/contest")
    contests.forEach {
        it.path = it.path.removePrefix(workPath + File.separator)
    }

    log.info("读取模版文件、生成模版引擎...")
    val resource = ResourceUtil.getUtf8Reader("readme.md.template").readText()
    val engine = TemplateUtil.createEngine(TemplateConfig())
    val template = engine.getTemplate(resource)
    val readme = template.render(Dict.create().set("problems", problems).set("contests", contests))
    log.info("文件内容渲染完成")

    log.info("输出生成readme.md...")
    val file = FileUtil.file("${workPath}/readme.md")
    FileUtil.writeUtf8String(readme, file)

    log.info("结束生成readme.md")
}