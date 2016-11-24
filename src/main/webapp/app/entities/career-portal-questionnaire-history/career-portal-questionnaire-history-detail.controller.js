(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireHistoryDetailController', CareerPortalQuestionnaireHistoryDetailController);

    CareerPortalQuestionnaireHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CareerPortalQuestionnaireHistory'];

    function CareerPortalQuestionnaireHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, CareerPortalQuestionnaireHistory) {
        var vm = this;

        vm.careerPortalQuestionnaireHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:careerPortalQuestionnaireHistoryUpdate', function(event, result) {
            vm.careerPortalQuestionnaireHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
