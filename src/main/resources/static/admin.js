var storage = Storages.localStorage;
var headerText = $("#headerText");

$(function () {
    var mainContainer = $("#mainContainer");
    var questionListJson;

    //"Main функция"
    $(document).ready(function () {
        chooseForm();
        $(document).on("hashchange", chooseForm);
    });


    function chooseForm() {
        if (Router.path[0] === "reset") {
            setDefaultSession();
            //$(document).trigger("hashchange");
        }

        if (!storage.isSet("login")) {
            loadContent("adminTpl/opening/opening.dust", "adminTpl/opening/opening.js");
        } else {
            fetchQuestionList(function(){
                loadContent("adminTpl/editor/editor.dust", "adminTpl/editor/editor.js", questionListJson);
            }, function(){
                alert("Error");
            });
            
        }
    }

    /*
     * Загружает и выкладывает на страницу отрендеренный шаблон,   
     * а так же загружает принадлежащий ему javascript файл. 
     *   (string) tpl - Путь к файлу шаблона
     *   (string) js - Путь к файлу javascript
     *   (object) tplData - Данные для шаблонизатора
     *   Ничего не возвращает
     */
    function loadContent(tpl, js, tplData) {
        if (typeof (tpl) !== "string" || typeof (js) !== "string") {
            console.log("В функцию loadContent переданы неверные аргументы.");
            console.trace();
            return;
        }

        //Попытка загрузить шаблон
        $.ajax({
            url: tpl,
            type: "GET"
        }).done(function (tplString) {
            dust.renderSource(tplString, tplData, function (err, out) {
                mainContainer.empty().append(out);
                //Попытка загрузить скрипт
                $.ajax({
                    url: js
                }).done(function (script) {
                    if (script === "") {
                        console.log("Получен пустой скрипт");
                        console.trace();
                        return;
                    }
                }).fail(function (errorText) {
                    console.trace();
                });
            });
        }).fail(function (errorText) {
            console.trace();
        });
    }
    
    /*
     * Запрашивает список вопросов у сервера.
     *  (callback) doneCallback - выполняется в случае успеха
     *  (callback) failCallback - выполняется в случае неудачи
     *  Ничего не возвращает
     */
    function fetchQuestionList(doneCallback, failCallback) {
        $.ajax({
            url: "test/db.json",
            type: "GET"
        }).done(function (questionsJson) {
            questionListJson = questionsJson;
            doneCallback();
        }).fail(function () {
            failCallback();
        });
    }

});

function setDefaultSession() {
    storage.removeAll();
}

/*
 * Меняет надпись в заголовке страницы.
 *   (string) str - текст
 *   Ничего не возвращает
 */
function setHeaderText(text) {
    if (typeof (text) !== "string") {
        console.log("В функцию headerText передан неверный аргумент");
        console.trace();
        return;
    }
    headerText.text(text);
};
