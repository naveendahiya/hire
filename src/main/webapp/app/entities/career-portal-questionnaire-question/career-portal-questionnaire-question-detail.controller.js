(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireQuestionDetailController', CareerPortalQuestionnaireQuestionDetailController);

    CareerPortalQuestionnaireQuestionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CareerPortalQuestionnaireQuestion'];

    function CareerPortalQuestionnaireQuestionDetailController($scope, $rootScope, $stateParams, previousState, entity, CareerPortalQuestionnaireQuestion) {
        var vm = this;

        vm.careerPortalQuestionnaireQuestion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:careerPortalQuestionnaireQuestionUpdate', function(event, result) {
            vm.careerPortalQuestionnaireQuestion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
