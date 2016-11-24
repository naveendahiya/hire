(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireAnswerDetailController', CareerPortalQuestionnaireAnswerDetailController);

    CareerPortalQuestionnaireAnswerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CareerPortalQuestionnaireAnswer'];

    function CareerPortalQuestionnaireAnswerDetailController($scope, $rootScope, $stateParams, previousState, entity, CareerPortalQuestionnaireAnswer) {
        var vm = this;

        vm.careerPortalQuestionnaireAnswer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:careerPortalQuestionnaireAnswerUpdate', function(event, result) {
            vm.careerPortalQuestionnaireAnswer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
