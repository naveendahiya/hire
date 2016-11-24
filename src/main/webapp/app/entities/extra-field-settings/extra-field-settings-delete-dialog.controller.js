(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtraFieldSettingsDeleteController',ExtraFieldSettingsDeleteController);

    ExtraFieldSettingsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExtraFieldSettings'];

    function ExtraFieldSettingsDeleteController($uibModalInstance, entity, ExtraFieldSettings) {
        var vm = this;

        vm.extraFieldSettings = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExtraFieldSettings.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
