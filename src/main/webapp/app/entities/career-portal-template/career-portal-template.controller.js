(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalTemplateController', CareerPortalTemplateController);

    CareerPortalTemplateController.$inject = ['$scope', '$state', 'CareerPortalTemplate'];

    function CareerPortalTemplateController ($scope, $state, CareerPortalTemplate) {
        var vm = this;

        vm.careerPortalTemplates = [];

        loadAll();

        function loadAll() {
            CareerPortalTemplate.query(function(result) {
                vm.careerPortalTemplates = result;
            });
        }
    }
})();
