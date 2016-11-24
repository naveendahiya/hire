(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireQuestionController', CareerPortalQuestionnaireQuestionController);

    CareerPortalQuestionnaireQuestionController.$inject = ['$scope', '$state', 'CareerPortalQuestionnaireQuestion'];

    function CareerPortalQuestionnaireQuestionController ($scope, $state, CareerPortalQuestionnaireQuestion) {
        var vm = this;

        vm.careerPortalQuestionnaireQuestions = [];

        loadAll();

        function loadAll() {
            CareerPortalQuestionnaireQuestion.query(function(result) {
                vm.careerPortalQuestionnaireQuestions = result;
            });
        }
    }
})();
