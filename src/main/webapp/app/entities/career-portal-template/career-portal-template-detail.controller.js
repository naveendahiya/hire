(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalTemplateDetailController', CareerPortalTemplateDetailController);

    CareerPortalTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CareerPortalTemplate'];

    function CareerPortalTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, CareerPortalTemplate) {
        var vm = this;

        vm.careerPortalTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:careerPortalTemplateUpdate', function(event, result) {
            vm.careerPortalTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
