(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireHistoryController', CareerPortalQuestionnaireHistoryController);

    CareerPortalQuestionnaireHistoryController.$inject = ['$scope', '$state', 'CareerPortalQuestionnaireHistory'];

    function CareerPortalQuestionnaireHistoryController ($scope, $state, CareerPortalQuestionnaireHistory) {
        var vm = this;

        vm.careerPortalQuestionnaireHistories = [];

        loadAll();

        function loadAll() {
            CareerPortalQuestionnaireHistory.query(function(result) {
                vm.careerPortalQuestionnaireHistories = result;
            });
        }
    }
})();
