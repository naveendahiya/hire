(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalTemplateDeleteController',CareerPortalTemplateDeleteController);

    CareerPortalTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'CareerPortalTemplate'];

    function CareerPortalTemplateDeleteController($uibModalInstance, entity, CareerPortalTemplate) {
        var vm = this;

        vm.careerPortalTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CareerPortalTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
