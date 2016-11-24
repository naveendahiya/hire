(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireController', CareerPortalQuestionnaireController);

    CareerPortalQuestionnaireController.$inject = ['$scope', '$state', 'CareerPortalQuestionnaire'];

    function CareerPortalQuestionnaireController ($scope, $state, CareerPortalQuestionnaire) {
        var vm = this;

        vm.careerPortalQuestionnaires = [];

        loadAll();

        function loadAll() {
            CareerPortalQuestionnaire.query(function(result) {
                vm.careerPortalQuestionnaires = result;
            });
        }
    }
})();
