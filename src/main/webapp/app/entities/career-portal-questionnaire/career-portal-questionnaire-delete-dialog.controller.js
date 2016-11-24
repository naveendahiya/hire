(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireDeleteController',CareerPortalQuestionnaireDeleteController);

    CareerPortalQuestionnaireDeleteController.$inject = ['$uibModalInstance', 'entity', 'CareerPortalQuestionnaire'];

    function CareerPortalQuestionnaireDeleteController($uibModalInstance, entity, CareerPortalQuestionnaire) {
        var vm = this;

        vm.careerPortalQuestionnaire = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CareerPortalQuestionnaire.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
