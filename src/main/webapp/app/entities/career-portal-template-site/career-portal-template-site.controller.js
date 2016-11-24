(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalTemplateSiteController', CareerPortalTemplateSiteController);

    CareerPortalTemplateSiteController.$inject = ['$scope', '$state', 'CareerPortalTemplateSite'];

    function CareerPortalTemplateSiteController ($scope, $state, CareerPortalTemplateSite) {
        var vm = this;

        vm.careerPortalTemplateSites = [];

        loadAll();

        function loadAll() {
            CareerPortalTemplateSite.query(function(result) {
                vm.careerPortalTemplateSites = result;
            });
        }
    }
})();
