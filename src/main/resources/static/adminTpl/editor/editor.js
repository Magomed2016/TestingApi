$(
    function () {
        var sideBarElems = $("#questions a");
        var topicsSideBarElems = $("#topcis a");
        var answerForm = $("#answerForm");
        var curQuestionText;
        var answers;
        var inputs = $();
        var select;
        var curQuestionJson;
        var formDust;
        var addFieldButton;
        var answerBox;
        var questionTplJson = {
            question: "",
            type: "radio",
            answers: [{
                num: 1,
                option: "ответ 1"
            }]
        };

        //"Main функция" 
        $(document).ready(function () {
            setHeaderText("Редактор");

            sideBarElems.on("click", function () {
                sideBarElems.removeClass("active");
                $(this).addClass("active");
                location.hash = $(this).prop("href").split("#")[1];
                Router.update();
                if (Router.path[0] !== "-1") {
                    requestQuestion(Router.path[0], function () {
                        if (formDust === undefined) {
                            requestFormDust(function () {
                                renderForm();
                            }, function () {
                                alert("Error");
                            });
                        } else {
                            renderForm();
                        }
                    }, function () {
                        alert("Error");
                    });
                } else {
                    curQuestionJson = questionTplJson;
                    if (formDust === undefined) {
                        requestFormDust(function () {
                            renderForm();
                        }, function () {
                            alert("Error");
                        });
                    } else {
                        renderForm();
                    }
                }

            });
        });

        function unBindAll() {
            sideBarElems.unbind();
        }

        /*
         * Запрашивает вопрос у сервера.
         *  (number) id - id вопроса 
         *  (callback) doneCallback - выполняется в случае успеха
         *  (callback) failCallback - выполняется в случае неудачи
         *  Ничего не возвращает
         */
        function requestQuestion(id, doneCallback, failCallback) {
            $.ajax({
                url: "test/q" + id + ".json",
                type: "GET"
            }).done(function (questionJson) {
                curQuestionJson = questionJson;
                doneCallback();
            }).fail(function () {
                failCallback();
            });
        }

        /*
         * Запрашивает форму у сервера.
         *  (callback) doneCallback - выполняется в случае успеха
         *  (callback) failCallback - выполняется в случае неудачи
         *  Ничего не возвращает
         */
        function requestFormDust(doneCallback, failCallback) {
            $.ajax({
                url: "adminTpl/editor/form.dust",
                type: "GET"
            }).done(function (formTpl) {
                formDust = formTpl;
                doneCallback();
            }).fail(function () {
                failCallback();
            });
        }

        function renderForm() {
            answerForm.empty();
            //Сделать сдесь unBind!!!!!!
            dust.renderSource(formDust, curQuestionJson, function (err, out) {
                var temp;
                unBindDeleteRefs();
                answerForm.append(out);
                bindDeleteRefs();
                inputs = $("input");
                select = $("select");
                curQuestionText = $("textarea").first();
                select.val(curQuestionJson["type"]);
                addFieldButton = $("#addField");
                temp = $(".answerBox");
                if (temp.length > 0) {
                    answerBox = $(".answerBox")[0].outerHTML;
                }
                answers = $("#answers");
                select.on("change", changeInputType);
                if (curQuestionJson["type"] === "textarea") {
                    addFieldButton.unbind();
                    addFieldButton.addClass("disabled");
                } else {
                    addFieldButton.remove("disabled");
                    addFieldButton.on("click", function () {
                        unBindDeleteRefs();
                        answers.append(answerBox);
                        bindDeleteRefs();
                        inputs = $("input");
                        changeInputType();
                    });
                }
            });
            selectRightAnswers();
        }

        function changeInputType() {
            //if (select.val() !== "textarea") {
            if (inputs.length > 0 && select.val() !== "textarea") {
                inputs.prop("type", select.val());
            } else if (inputs.length === 0) {
                questionTplJson.type = select.val();
                questionTplJson.question = curQuestionText.val();
                curQuestionJson = questionTplJson;
                renderForm();
            } else {
                questionTplJson.type = select.val();
                questionTplJson.question = curQuestionText.val();
                curQuestionJson = questionTplJson;
                renderForm();
            }
        }

        //Исправить!
        function sendQuestionJson() {
            var questionJson = {};
            var i = 0;
            questionJson.question = curQuestionText.val();
            questionJson.type = select.val();
            questionJson.rightAnswer = $(":checked").map(function () {
                $(this).val();
            }).get();
            if (questionJson.type !== "textarea") {
                questionJson.answers[i] = {
                    num: i,
                    option: inputs.get(i + 1).find("textarea").val()
                };
            } else {
                questionJson.answers[0] = {
                    num: 0,
                    option: $("textarea").val()
                };
            }
            //Отправка на сервер
            if (Router.path[0] === "-1") {
                //Создать новый вопрос
            }

        }

        function bindDeleteRefs() {
            $(".delete").on("click", function () {
                $(this).closest(".answerBox").remove();
            });
        }

        function unBindDeleteRefs() {
            $(".delete").unbind();
        }

        function selectRightAnswers() {
            if (curQuestionJson.type === "textarea") {
                return;
            }
            inputs.prop("checked", false);
            curQuestionJson.rightAnswer.forEach(function (item, i, arr) {
                inputs.eq(item).prop("checked", true);
            });

        }
    }
);
