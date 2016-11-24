(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalTemplateSiteDeleteController',CareerPortalTemplateSiteDeleteController);

    CareerPortalTemplateSiteDeleteController.$inject = ['$uibModalInstance', 'entity', 'CareerPortalTemplateSite'];

    function CareerPortalTemplateSiteDeleteController($uibModalInstance, entity, CareerPortalTemplateSite) {
        var vm = this;

        vm.careerPortalTemplateSite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CareerPortalTemplateSite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
