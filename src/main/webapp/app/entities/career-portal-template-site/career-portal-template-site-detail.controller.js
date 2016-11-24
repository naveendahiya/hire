(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalTemplateSiteDetailController', CareerPortalTemplateSiteDetailController);

    CareerPortalTemplateSiteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CareerPortalTemplateSite'];

    function CareerPortalTemplateSiteDetailController($scope, $rootScope, $stateParams, previousState, entity, CareerPortalTemplateSite) {
        var vm = this;

        vm.careerPortalTemplateSite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:careerPortalTemplateSiteUpdate', function(event, result) {
            vm.careerPortalTemplateSite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
