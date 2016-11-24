(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireAnswerController', CareerPortalQuestionnaireAnswerController);

    CareerPortalQuestionnaireAnswerController.$inject = ['$scope', '$state', 'CareerPortalQuestionnaireAnswer'];

    function CareerPortalQuestionnaireAnswerController ($scope, $state, CareerPortalQuestionnaireAnswer) {
        var vm = this;

        vm.careerPortalQuestionnaireAnswers = [];

        loadAll();

        function loadAll() {
            CareerPortalQuestionnaireAnswer.query(function(result) {
                vm.careerPortalQuestionnaireAnswers = result;
            });
        }
    }
})();
