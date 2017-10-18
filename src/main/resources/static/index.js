/*
 * state:
 * 0 - Начальный экран
 * 1 - Тестирование
 * 2 - Тестирование завершено
 */

var headerText = $("#headerText");
var storage = Storages.localStorage;
  storage.set("object",'[');
  storage.set("topicId","1");

$(
    function () {
        var mainContainer = $("#mainContainer");
        var curQuestionJson;
        
        //"Main функция" 
        $(document).ready(function () {
            setHeaderText("2");
            if (!storage.isSet("inSession")) {
                setDefaultSessionParams();
            }
            chooseForm();
            $(document).on("hashchange", chooseForm);
        });

        function chooseForm() {
            var state = storage.get("state");
            if (Router.path[0] === "reset") {
                setDefaultSessionParams();
            }
            switch (state) {
                case 0:
                    {
                        loadContent("tpl/opening/opening.dust", "tpl/opening/opening.js");
                        break;
                    }
                case 1:
                    {
                        requestQuestion(function () {
                            loadContent("tpl/testing/testing.dust", "tpl/testing/testing.js", curQuestionJson);
                        }, chooseForm);
                        break;
                    }
                case 2:
                    {
                        loadContent("tpl/testingEnd/testingEnd.dust", "tpl/testingEnd/testingEnd.js");
                        break;
                    }
            }
        }
        /*
         * Загружает и выкладывает на страницу отрендеренный шаблон,   
         * а так же загружает принадлежащий ему javascript файл. 
         * Выполняется асинхронно.
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
         * Запрашивает вопрос у сервера.
         *  (callback) doneCallback - выполняется в случае успеха
         *  (callback) failCallback - выполняется в случае неудачи
         *  Ничего не возвращает
         */
        function requestQuestion(doneCallback, failCallback) {
            
           
            
            
            $.getJSON("/api/testing/questions/"+ storage.get("questionNum"), function(data) {
                

                    curQuestionJson = data;
                    doneCallback();
            }).fail(function(){
                storage.set("testingEnded", true);
                storage.set("state", 2);
                failCallback();

            });

         
//            $.ajax({
//                url: "/api/editor/questions/" + storage.get("questionNum"),
//                type: "GET"
//            }).done(function (questionJson) {
//                curQuestionJson = questionJson;
//                doneCallback();
//            }).fail(function () {
//                storage.set("testingEnded", true);
//                storage.set("state", 2);
//                failCallback();
//            });
        }

    }
);

/*
 * Устанавливает настройки сессии по умолчанию.
 * Пользователь увидит начальный экран.
 *   Ничего не возвращает
 */
function setDefaultSessionParams() {
    storage.set("state", 0);
    storage.set("inSession", true);
    storage.set("questionNum", 1);
    storage.set("testingEnded", false);
    storage.remove("firstName");
    storage.remove("lastName");
    storage.remove("email");
};

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
