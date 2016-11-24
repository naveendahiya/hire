(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedListEntryDeleteController',SavedListEntryDeleteController);

    SavedListEntryDeleteController.$inject = ['$uibModalInstance', 'entity', 'SavedListEntry'];

    function SavedListEntryDeleteController($uibModalInstance, entity, SavedListEntry) {
        var vm = this;

        vm.savedListEntry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SavedListEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
