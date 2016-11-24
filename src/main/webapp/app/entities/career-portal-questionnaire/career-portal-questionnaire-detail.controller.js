(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireDetailController', CareerPortalQuestionnaireDetailController);

    CareerPortalQuestionnaireDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CareerPortalQuestionnaire'];

    function CareerPortalQuestionnaireDetailController($scope, $rootScope, $stateParams, previousState, entity, CareerPortalQuestionnaire) {
        var vm = this;

        vm.careerPortalQuestionnaire = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:careerPortalQuestionnaireUpdate', function(event, result) {
            vm.careerPortalQuestionnaire = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
